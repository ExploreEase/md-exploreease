package com.ch2ps418.travelapp.presentation.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ch2ps418.travelapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private val viewModel: HomeFragmentViewModel by viewModels()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		// Inflate the layout for this fragment
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.getDeviceToken().observe(viewLifecycleOwner) { deviceToken ->
			// Update your UI with the deviceToken value
			binding.testToken.text = deviceToken

			viewModel.getNearestPlaces(deviceToken.toString(), -7.052945994551127, 110.44020676422383)

		}


		viewModel.placesResult.observe(viewLifecycleOwner){
//			Log.d("MESSAGE", it.toString())
		}

	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}