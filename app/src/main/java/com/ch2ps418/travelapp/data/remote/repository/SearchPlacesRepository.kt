package com.ch2ps418.travelapp.data.remote.repository

import com.ch2ps418.travelapp.data.remote.retrofit.datasource.SearchPlacesRemoteDataSource
import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.wrapper.Resource
import javax.inject.Inject

interface SearchPlacesRepository {
	suspend fun getSearchPlace(
		devicetoken: String,
		placename: String,
	): Resource<BackendResponse>
}

class SearchPlacesRepositoryImpl @Inject constructor(private val dataSource: SearchPlacesRemoteDataSource) :
	SearchPlacesRepository {
	override suspend fun getSearchPlace(
		devicetoken: String,
		placename: String
	): Resource<BackendResponse> {
		return proceed {
			dataSource.getSearchPlace(devicetoken, placename)
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