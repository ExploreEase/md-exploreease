package com.ch2ps418.travelapp.presentation.ui.home.home.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch2ps418.travelapp.data.local.datastore.DataStoreManager
import com.ch2ps418.travelapp.data.remote.repository.CategoryPlacesRepository
import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryFragmentViewModel @Inject constructor(
	private val dataStoreManager: DataStoreManager,
	private val repository: CategoryPlacesRepository,
) : ViewModel() {

	private val _placesResult = MutableLiveData<Resource<BackendResponse>>()
	fun getDeviceToken(): LiveData<String?> = dataStoreManager.getDeviceToken.asLiveData()

	fun getLatUser(): LiveData<Double?> = dataStoreManager.getLatUser.asLiveData()
	fun getLonUser(): LiveData<Double?> = dataStoreManager.getLonUser.asLiveData()

//	fun setDeviceToken(deviceToken: String) = CoroutineScope(Dispatchers.IO).launch {
//		dataStoreManager.setDeviceToken(deviceToken)
//	}


	fun getSearchByCategory(deviceToken: String, lat: Double, lon: Double, usercategory:String) {
		viewModelScope.launch(Dispatchers.IO) {
			_placesResult.postValue(Resource.Loading())
			try {
				val data = repository.getSearchByCategory(deviceToken, lat, lon, usercategory)

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