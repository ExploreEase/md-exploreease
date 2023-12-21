package com.ch2ps418.travelapp.data.remote.repository

import com.ch2ps418.travelapp.data.remote.retrofit.datasource.NearestPlacesRemoteDataSource
import com.ch2ps418.travelapp.data.remote.retrofit.datasource.TopPlacesRemoteDataSource
import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.wrapper.Resource
import javax.inject.Inject

interface TopPlacesRepository {
	suspend fun getTopPlaces(
		devicetoken: String,
		lat: Double,
		lon: Double,
	): Resource<BackendResponse>
}

class TopPlacesRepositoryImpl @Inject constructor(private val dataSource: TopPlacesRemoteDataSource) :
	TopPlacesRepository {
	override suspend fun getTopPlaces(
		devicetoken: String,
		lat: Double,
		lon: Double,
	): Resource<BackendResponse> {
		return proceed {
			dataSource.getTopPlaces(devicetoken, lat, lon)
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