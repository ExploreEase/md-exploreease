package com.ch2ps418.travelapp.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ch2ps418.travelapp.R
import com.ch2ps418.travelapp.databinding.FragmentLoginBinding
import com.ch2ps418.travelapp.presentation.ui.auth.AuthViewModel
import com.ch2ps418.travelapp.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

	private var _binding: FragmentLoginBinding? = null
	private val binding get() = _binding!!

	private val viewModel: AuthViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentLoginBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

        buttonClickListener()
	}

	private fun buttonClickListener() {
		val navController = findNavController()

		// Update your click listener
		binding.btnRegisterAlternate.setOnClickListener {
			// Replace the explicit Intent with NavController and action
			navController.navigate(R.id.action_loginFragment_to_registerFragment)
		}

		binding.btnLogin.setOnClickListener {
			setLoading(true)

			if (validateForm()) {
				val enteredEmail = binding.edLoginEmail.text.toString().trim()
				val enteredPassword = binding.edLoginPassword.text.toString().trim()

				viewModel.getEmail().observe(viewLifecycleOwner) { email ->
					val savedEmail = email ?: ""

					viewModel.getPassword().observe(viewLifecycleOwner) { password ->

						val savedPassword = password ?: ""

						// Menggunakan Handler untuk menambahkan delay 2 detik
						Handler().postDelayed({

							if (enteredEmail == savedEmail && enteredPassword == savedPassword) {
								val intent = Intent(requireContext(), HomeActivity::class.java)
								startActivity(intent)
								setLoading(false)
								viewModel.setIsLogin(true)

							} else {
								Toast.makeText(
									requireContext(),
									"Invalid credentials",
									Toast.LENGTH_SHORT
								).show()

								setLoading(false)
							}

						}, 2000) // Delay 2 detik (2000 milidetik)
					}
				}
			}
		}

	}

	private fun validateForm(): Boolean {
		val email = binding.edLoginEmail.text.toString()
		val password = binding.edLoginPassword.text.toString()

		var isFormValid = true

		if (email.isEmpty()) {
			isFormValid = false
			binding.tilLoginEmail.error = getString(R.string.tv_error_input_blank)
		} else {
			binding.tilLoginEmail.error = null
		}

		if (password.isEmpty()) {
			isFormValid = false
			binding.tilLoginPassword.error = getString(R.string.tv_error_input_blank)
		} else {
			binding.tilLoginPassword.error = null
		}

		return isFormValid
	}

	private fun setLoading(isLoading: Boolean) {
//		binding.pbLogin.isVisible = isLoading
	}
}