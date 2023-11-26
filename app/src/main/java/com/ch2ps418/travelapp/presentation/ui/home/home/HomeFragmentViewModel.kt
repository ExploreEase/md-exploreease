package com.ch2ps418.travelapp.presentation.ui.home.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch2ps418.travelapp.data.local.datastore.DataStoreManager
import com.ch2ps418.travelapp.data.remote.repository.NearestPlacesRepository
import com.ch2ps418.travelapp.data.remote.retrofit.model.NearestPlacesResponse
import com.ch2ps418.travelapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
	private val dataStoreManager: DataStoreManager,
	private val repository: NearestPlacesRepository,
) : ViewModel() {

	private val _placesResult = MutableLiveData<Resource<NearestPlacesResponse>>()
	val placesResult: LiveData<Resource<NearestPlacesResponse>> get() = _placesResult

	fun getDeviceToken(): LiveData<String?> = dataStoreManager.getDeviceToken.asLiveData()

	fun setDeviceToken(deviceToken: String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setDeviceToken(deviceToken)
	}


	fun getNearestPlaces(deviceToken: String, lat: Double, lon: Double) {
		viewModelScope.launch(Dispatchers.IO) {
			_placesResult.postValue(Resource.Loading())
			try {
				val data = repository.getNearestPlaces(deviceToken, lat, lon)

				Log.d("PAYLOAD", data.payload.toString())
				if (data.payload != null) {
					viewModelScope.launch(Dispatchers.Main) {
						_placesResult.postValue(Resource.Success(data.payload))
					}
				} else {
					_placesResult.postValue(Resource.Error(data.exception, null))
				}
			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_placesResult.postValue(Resource.Error(e, null))
				}
			}
		}
	}
}