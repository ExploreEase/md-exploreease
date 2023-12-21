package com.ch2ps418.travelapp.data.remote.retrofit.datasource

import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.data.remote.retrofit.service.ApiServicePlaces
import javax.inject.Inject

interface SearchPlacesRemoteDataSource {
	suspend fun getSearchPlace(devicetoken: String,placename: String): BackendResponse
}

class SearchPlacesRemoteDataSourceImpl @Inject constructor(private val apiServicePlaces: ApiServicePlaces) :
	SearchPlacesRemoteDataSource {
	override suspend fun getSearchPlace(
		devicetoken: String,
		placename: String,
	): BackendResponse {
		return apiServicePlaces.getSearchPlace(devicetoken, placename)
	}


}