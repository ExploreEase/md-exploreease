package com.ch2ps418.travelapp.presentation.ui.home.home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2ps418.travelapp.R
import com.ch2ps418.travelapp.data.remote.firebase.model.Place
import com.ch2ps418.travelapp.databinding.FragmentHomeBinding
import com.ch2ps418.travelapp.presentation.ui.home.home.adapter.CategoryAdapter
import com.ch2ps418.travelapp.presentation.ui.home.home.adapter.PlaceAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private val viewModel: HomeFragmentViewModel by viewModels()
	private lateinit var fusedLocationClient: FusedLocationProviderClient

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		isLoading(true)

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

		viewModel.getStatusOnboarding().observe(viewLifecycleOwner) { isAlreadyOnboarding ->
			Log.d("ONBOARDING", isAlreadyOnboarding.toString())
		}
		viewModel.getDeviceToken().observe(viewLifecycleOwner) { deviceToken ->

			if (checkLocationPermission()) {
				// Permission granted, get the current location
				getCurrentLocation(deviceToken.toString())
			} else {
				// Request location permission
				requestLocationPermission()
			}
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			// Create channel to show notifications.
			val channelId = getString(R.string.default_notification_channel_id)
			val channelName = getString(R.string.default_notification_channel_name)
			val notificationManager =
				requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

			notificationManager.createNotificationChannel(
				NotificationChannel(
					channelId,
					channelName,
					NotificationManager.IMPORTANCE_LOW,
				),
			)

			askNotificationPermission()
		}

		LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
			object : BroadcastReceiver() {
				override fun onReceive(context: Context?, intent: Intent?) {
					intent?.let {
						val tenNearestPlaces =
							it.getSerializableExtra("tenNearestPlaces") as? List<Place>

						tenNearestPlaces?.let {
							// Update your adapter with the new data
							binding.rvPlace.layoutManager =
								LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
							binding.rvPlace.adapter = PlaceAdapter(tenNearestPlaces)
							isLoading(false)

						}
					}
				}
			},
			IntentFilter("MyCustomAction")
		)

		/*LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
			object : BroadcastReceiver() {
				override fun onReceive(context: Context?, intentTopPlaces: Intent?) {
					intentTopPlaces?.let {
						val tenTopPlaces =
							it.getSerializableExtra("tenTopPlaces") as? List<Place>

						tenTopPlaces?.let {
							// Update your adapter with the new data
							binding.rvTopPlaces.layoutManager =
								LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
							binding.rvTopPlaces.adapter = PlaceAdapter(tenTopPlaces)
							isLoading(false)

						}
					}
				}
			},
			IntentFilter("MyCustomActionTopPlaces")
		)*/

		val categories = listOf("Alam", "Alam Buatan", "Adat", "Agrowisata", "Bahari", "Budaya", "Cagar", "Cagar Alam",
								"Cagar Budaya", "Desa Wisata", "Hiburan", "Ibadah", "Margasatwa dan Budaya", "Margasatwa", "Pemandian Alam",
								"Pusat Perbelanjaan", "Perbelanjaan", "Religi", "Sejarah", "Taman", "Taman Air", "Taman Hiburan",
								"Taman Kopi", "Taman Margasatwa", "Taman Wisata", "Tempat Hiburan", "Tempat Ibadah", "Tradisional", "", )
		val adapter = CategoryAdapter(categories)
		binding.rvCategory.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		binding.rvCategory.adapter = adapter

	}

	private fun isLoading(isLoading: Boolean){

		if (isLoading){
			binding.constraintHome.visibility = View.GONE
			binding.pbHome.visibility = View.VISIBLE
		} else {
			binding.constraintHome.visibility = View.VISIBLE
			binding.pbHome.visibility = View.GONE
		}
	}

	private val requestPermissionLauncher = registerForActivityResult(
		ActivityResultContracts.RequestPermission(),
	) { isGranted: Boolean ->
		if (isGranted) {
			Toast.makeText(requireContext(), "Notifications permission granted", Toast.LENGTH_SHORT)
				.show()
		} else {
			Toast.makeText(
				requireContext(),
				"FCM can't post notifications without POST_NOTIFICATIONS permission",
				Toast.LENGTH_LONG,
			).show()
		}
	}

	private fun askNotificationPermission() {
		// This is only necessary for API Level > 33 (TIRAMISU)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (ContextCompat.checkSelfPermission(
					requireContext(),
					Manifest.permission.POST_NOTIFICATIONS
				) ==
				PackageManager.PERMISSION_GRANTED
			) {
				// FCM SDK (and your app) can post notifications.
			} else {
				// Directly ask for the permission
				requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
			}
		}
	}

	private fun checkLocationPermission(): Boolean {
		return ContextCompat.checkSelfPermission(
			requireContext(),
			Manifest.permission.ACCESS_FINE_LOCATION
		) == PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(
					requireContext(),
					Manifest.permission.ACCESS_COARSE_LOCATION
				) == PackageManager.PERMISSION_GRANTED
	}

	private fun requestLocationPermission() {
		showLocationPermissionDialog()
	}

	private fun showLocationPermissionDialog() {
		val alertDialog: AlertDialog = AlertDialog.Builder(requireContext())
			.setTitle("Location Permission")
			.setMessage("This app needs location permission to fetch your current location.")
			.setPositiveButton("Grant") { _, _ ->
				requestPermissions(
					arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
					LOCATION_PERMISSION_REQUEST_CODE
				)
			}
			.setNegativeButton("Deny") { dialog, _ -> dialog.dismiss() }
			.create()

		alertDialog.show()
	}

	private fun getCurrentLocation(deviceToken: String) {
		if (ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return
		}
		fusedLocationClient.lastLocation.addOnSuccessListener { location ->
			if (location != null) {
				// If last known location is available, use it
				viewModel.getNearestPlaces(
					deviceToken,
					location.latitude,
					location.longitude
				)
			} else {
				// Handle the case when last known location is null
				// You may want to show a message to the user or take appropriate action

				Toast.makeText(
					requireContext(),
					"Location is null, fetching place near default location: Semarang!",
					Toast.LENGTH_SHORT
				).show()
				viewModel.getNearestPlaces(
					deviceToken,
					-7.052945994551127,
					110.44020676422383
				)
			}
		}
	}

	@Deprecated("Deprecated in Java")
	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray,
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permission granted, get the current location
				viewModel.getDeviceToken().value?.let { deviceToken ->
					getCurrentLocation(deviceToken)
				}
			} else {
				// Permission denied, handle accordingly
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null

	}

	companion object {
		private const val LOCATION_PERMISSION_REQUEST_CODE = 100
	}
}
