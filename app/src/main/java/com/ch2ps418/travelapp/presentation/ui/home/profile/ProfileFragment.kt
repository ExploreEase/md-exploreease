package com.ch2ps418.travelapp.presentation.ui.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ch2ps418.travelapp.databinding.FragmentProfileBinding
import com.ch2ps418.travelapp.presentation.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {



	private var _binding: FragmentProfileBinding? = null
	private val binding get() = _binding!!

	private val viewModel: ProfileFagmentViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentProfileBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		checkLogin()
	}

	private fun checkLogin(){

		viewModel.getIsLogin().observe(viewLifecycleOwner){ isLogin ->

			if (isLogin == false){

				val intent = Intent(requireActivity(), AuthActivity::class.java)
				startActivity(intent)
				requireActivity().finishAffinity()

			} else {

				viewModel.getName().observe(viewLifecycleOwner) { name ->
					binding.tvNameProfile.text = name
				}

				viewModel.getEmail().observe(viewLifecycleOwner) { email ->
					binding.tvEmailProfile.text = email
				}
			}
		}
	}
}