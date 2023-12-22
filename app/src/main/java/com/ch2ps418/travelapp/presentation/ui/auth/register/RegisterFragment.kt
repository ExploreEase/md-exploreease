package com.ch2ps418.travelapp.presentation.ui.auth.register

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
import com.ch2ps418.travelapp.databinding.FragmentRegisterBinding
import com.ch2ps418.travelapp.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonClickListener()
    }

    private fun buttonClickListener(){
        val navController = findNavController()

        // Update your click listener
        binding.btnLoginAlternate.setOnClickListener {
            // Replace the explicit Intent with NavController and action
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {

            setLoading(true)

            val name = binding.edtRegisterName.text.toString().trim()
            val email = binding.edtRegisterEmail.text.toString().trim()
            val password = binding.edtRegisterPassword.text.toString().trim()

            Handler().postDelayed({

                if (validateForm()) {
                    viewModel.setName(name)
                    viewModel.setEmail(email)
                    viewModel.setPassword(password)

                    Toast.makeText(requireContext(), "Register success!", Toast.LENGTH_SHORT).show()

                    navController.navigate(R.id.action_registerFragment_to_loginFragment)

                } else {
                    Toast.makeText(requireContext(), "Register invalid!", Toast.LENGTH_SHORT).show()
                }

                setLoading(false)

            }, 2000) // Delay 2 detik (2000 milidetik)
        }

    }

    private fun validateForm(): Boolean {
        val name = binding.edtRegisterName.text.toString()
        val email = binding.edtRegisterEmail.text.toString()
        val password = binding.edtRegisterPassword.text.toString()

        var isFormValid = true

        if (name.isEmpty()) {
            isFormValid = false
            binding.tilRegisterName.error = getString(R.string.tv_error_input_blank)
        } else {
            binding.tilRegisterName.error = null
        }

        if (email.isEmpty()) {
            isFormValid = false
            binding.tilRegisterEmail.error = getString(R.string.tv_error_input_blank)
        } else {
            binding.tilRegisterEmail.error = null
        }

        if (password.isEmpty()) {
            isFormValid = false
            binding.tilRegisterPassword.error = getString(R.string.tv_error_input_blank)
        } else {
            binding.tilRegisterPassword.error = null
        }

        return isFormValid
    }


    private fun setLoading(isLoading: Boolean) {
//        binding.pbRegister.isVisible = isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}