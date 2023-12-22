package com.ch2ps418.travelapp.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ch2ps418.travelapp.data.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val dataStoreManager: DataStoreManager,

) : ViewModel() {

	fun setIsLogin(status: Boolean) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setIsLogin(status)
	}
	fun setName(name:String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setName(name)
	}
	fun setEmail(email:String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setEmail(email)
	}
	fun setPassword(password:String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setPassword(password)
	}


	fun getEmail(): LiveData<String?> = dataStoreManager.getEmail.asLiveData()
	fun getPassword(): LiveData<String?> = dataStoreManager.getPassword.asLiveData()

}