package com.ch2ps418.travelapp.presentation.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ch2ps418.travelapp.data.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileFagmentViewModel @Inject constructor(
	private val dataStoreManager: DataStoreManager,
) : ViewModel() {
	fun getIsLogin(): LiveData<Boolean> = dataStoreManager.getIsLogin.asLiveData()

	fun getName(): LiveData<String?> = dataStoreManager.getName.asLiveData()
	fun getEmail(): LiveData<String?> = dataStoreManager.getEmail.asLiveData()

//	fun setDeviceToken(deviceToken: String) = CoroutineScope(Dispatchers.IO).launch {
//		dataStoreManager.setDeviceToken(deviceToken)
//	}

}