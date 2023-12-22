package com.ch2ps418.travelapp.data.remote.repository

import com.ch2ps418.travelapp.data.remote.retrofit.datasource.CategoryPlacesRemoteDataSource
import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.wrapper.Resource
import javax.inject.Inject

interface CategoryPlacesRepository {
	suspend fun getSearchByCategory(
		devicetoken: String,
		lat: Double,
		lon: Double,
		usercategory:String
	): Resource<BackendResponse>
}

class CategoryPlacesRepositoryImpl @Inject constructor(private val dataSource: CategoryPlacesRemoteDataSource) :
	CategoryPlacesRepository {
	override suspend fun getSearchByCategory(
		devicetoken: String,
		lat: Double,
		lon: Double,
		usercategory: String
	): Resource<BackendResponse> {
		return proceed {
			dataSource.getSearchByCategory(devicetoken, lat, lon, usercategory)
		}
	}

	private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
		return try {
			Resource.Success(coroutines.invoke())
		} catch (e: Exception) {
			Resource.Error(e)
		}
	}


}