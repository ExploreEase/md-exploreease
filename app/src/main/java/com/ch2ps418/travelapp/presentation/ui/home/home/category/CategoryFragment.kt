package com.ch2ps418.travelapp.presentation.ui.home.home.category

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2ps418.travelapp.data.remote.firebase.model.Place
import com.ch2ps418.travelapp.databinding.FragmentCategoryBinding
import com.ch2ps418.travelapp.presentation.ui.home.home.HomeFragmentArgs
import com.ch2ps418.travelapp.presentation.ui.home.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

	private var _binding: FragmentCategoryBinding? = null
	private val binding get() = _binding!!

	private val viewModel: CategoryFragmentViewModel by viewModels()

	private val args: HomeFragmentArgs by navArgs()

	private lateinit var category: String

	private val categoryBroadcastReceiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context?, intent: Intent?) {
			isLoading(false)

			intent?.let {
				val places =
					it.getSerializableExtra("categoryPlaces") as? List<Place>

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
				}
			} ?: run {
				// Handle the case where intent is null
				// For example, show an error message or hide the RecyclerView
				binding.rvPlace.visibility = View.GONE
			}
		}
	}
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentCategoryBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		init()

		setView()

		viewModel.getDeviceToken().observe(viewLifecycleOwner){  deviceToken->
			viewModel.getLatUser().observe(viewLifecycleOwner){ lat ->
				viewModel.getLonUser().observe(viewLifecycleOwner) { lon ->
					viewModel.getSearchByCategory(
						deviceToken.toString(),
						lat!!,
						lon!!,
						category
					)
				}
			}
		}


		LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
			categoryBroadcastReceiver,
			IntentFilter("Category Action")
		)
	}

	private fun init(){
		category = args.category
	}

	private fun setView(){
		binding.tvItemCategory.text = category
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
			.unregisterReceiver(categoryBroadcastReceiver)
	}
}