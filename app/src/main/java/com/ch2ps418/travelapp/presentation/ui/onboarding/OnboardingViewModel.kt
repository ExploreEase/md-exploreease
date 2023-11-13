package com.ch2ps418.travelapp.presentation.ui.onboarding

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
class OnboardingViewModel @Inject constructor(
	private val dataStoreManager: DataStoreManager
) : ViewModel() {

	fun getStatusOnboarding(): LiveData<Boolean> = dataStoreManager.getStatusOnboarding.asLiveData()

	fun setStatusOnboarding(status: Boolean) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setStatusOnboarding(status)
	}
}