package com.ch2ps418.travelapp.presentation.ui.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch2ps418.travelapp.data.local.datastore.DataStoreManager
import com.ch2ps418.travelapp.data.remote.repository.NearestPlacesRepository
import com.ch2ps418.travelapp.data.remote.repository.TopPlacesRepository
import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
	private val dataStoreManager: DataStoreManager,
	private val repository: NearestPlacesRepository,
	private val topPlacesRepository: TopPlacesRepository
) : ViewModel() {

	private val _placesResult = MutableLiveData<Resource<BackendResponse>>()
	val placesResult: LiveData<Resource<BackendResponse>> get() = _placesResult // LiveData untuk diobservasi di luar kelas

	fun getStatusOnboarding(): LiveData<Boolean> = dataStoreManager.getStatusOnboarding.asLiveData()



	fun getDeviceToken(): LiveData<String?> = dataStoreManager.getDeviceToken.asLiveData()


	fun getNearestPlaces(deviceToken: String, lat: Double, lon: Double) {
		viewModelScope.launch(Dispatchers.IO) {
			_placesResult.postValue(Resource.Loading())
			try {
				val data = repository.getNearestPlaces(deviceToken, lat, lon)

//				Log.d("PAYLOAD", data.payload.toString())
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

	fun getTopPlaces(deviceToken: String, lat: Double, lon: Double) {
		viewModelScope.launch(Dispatchers.IO) {
			_placesResult.postValue(Resource.Loading())
			try {
				val data = topPlacesRepository.getTopPlaces(deviceToken, lat, lon)

//				Log.d("PAYLOAD", data.payload.toString())
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