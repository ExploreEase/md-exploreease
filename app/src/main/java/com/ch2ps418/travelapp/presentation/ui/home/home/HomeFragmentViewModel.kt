package com.ch2ps418.travelapp.presentation.ui.home.home

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
class HomeFragmentViewModel @Inject constructor(
	private val dataStoreManager: DataStoreManager
) : ViewModel() {

	fun getDeviceToken(): LiveData<String?> = dataStoreManager.getDeviceToken.asLiveData()

	fun setDeviceToken(deviceToken: String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setDeviceToken(deviceToken)
	}
}