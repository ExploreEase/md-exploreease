package com.ch2ps418.travelapp.presentation.ui.home.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ch2ps418.travelapp.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private val viewModel: HomeFragmentViewModel by viewModels()
	private lateinit var fusedLocationClient: FusedLocationProviderClient

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

		viewModel.getDeviceToken().observe(viewLifecycleOwner) { deviceToken ->
			binding.testToken.text = deviceToken

			// Request location permission and get the current location
			requestLocationPermission()
		}

		viewModel.placesResult.observe(viewLifecycleOwner) {
			// Handle the result from fetching nearest places
		}
	}

	private fun requestLocationPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
			ContextCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {
			showLocationPermissionDialog()
		} else {
			// Permission already granted, get the current location
			getCurrentLocation()
		}
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

	private fun getCurrentLocation() {
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
				val latitude = location.latitude
				val longitude = location.longitude

				// fetch to backend
				viewModel.getNearestPlaces(
					binding.testToken.text.toString(),
					latitude,
					longitude
				)
			}
		}
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permission granted, get the current location
				getCurrentLocation()
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
