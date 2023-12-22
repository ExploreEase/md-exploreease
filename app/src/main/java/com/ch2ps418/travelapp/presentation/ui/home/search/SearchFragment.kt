package com.ch2ps418.travelapp.presentation.ui.home.search

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2ps418.travelapp.data.remote.firebase.model.Place
import com.ch2ps418.travelapp.databinding.FragmentSearchBinding
import com.ch2ps418.travelapp.presentation.ui.home.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

	private var _binding: FragmentSearchBinding? = null
	private val binding get() = _binding!!

	private val viewModel: SearchFagmentViewModel by viewModels()

	private val searchBroadcastReceiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context?, intent: Intent?) {
			isLoading(false)

			intent?.let {
				val places =
					it.getSerializableExtra("searchPlaces") as? List<Place>

				places?.let {
					binding.rvPlace.visibility = View.VISIBLE
					// Update your adapter with the new data
					binding.rvPlace.layoutManager =
						LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
					binding.rvPlace.adapter = SearchAdapter(places)
				} ?: run {
					// Handle the case where places is null
					// For example, show an error message or hide the RecyclerView
					binding.rvPlace.visibility = View.GONE
					binding.tvError.visibility = View.VISIBLE
				}
			} ?: run {
				// Handle the case where intent is null
				// For example, show an error message or hide the RecyclerView
				binding.rvPlace.visibility = View.GONE
				binding.tvError.visibility = View.VISIBLE
			}
		}
	}
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentSearchBinding.inflate(inflater, container, false)
		return binding.root
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		isLoading(false)

		binding.etSearch.setOnKeyListener { _, keyCode, event ->
			if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

				isLoading(true)
				binding.rvPlace.visibility = View.GONE
				binding.tvAskSearch.visibility = View.GONE

//				// Inisialisasi handler
//				val handler = Handler()
//
//				// Set a timeout using Handler
//				val timeoutMillis = 3000L // 3 seconds
//
//				handler.postDelayed({
//					// Handle the case where the loading took more than 3 seconds
//					// For example, show an error message or perform alternative actions
//					isLoading(false)
//					binding.rvPlace.visibility = View.GONE
//					binding.tvError.visibility = View.VISIBLE
//					binding.tvAskSearch.visibility = View.GONE
//				}, timeoutMillis)

				// Check if the search text is not null or empty
				val searchText = binding.etSearch.text.toString().trim()
				if (searchText.isNotEmpty()) {
					// Perform the API call only if the search text is not null or empty
					viewModel.getDeviceToken().observe(viewLifecycleOwner) { deviceToken ->
						viewModel.getSearchPlace(deviceToken.toString(), searchText)
					}
					return@setOnKeyListener true // Consume the key event
				}
			}
			return@setOnKeyListener false // Continue processing the key event
		}


		LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
			searchBroadcastReceiver,
			IntentFilter("Search Action")
		)

	}

	private fun isLoading(isLoading: Boolean) {

		if (isLoading){
			binding.pbSearch.visibility = View.VISIBLE
		} else {
			binding.pbSearch.visibility = View.GONE
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		LocalBroadcastManager.getInstance(requireContext())
			.unregisterReceiver(searchBroadcastReceiver)
	}
}
