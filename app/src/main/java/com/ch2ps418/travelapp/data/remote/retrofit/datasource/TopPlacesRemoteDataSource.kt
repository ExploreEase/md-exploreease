package com.ch2ps418.travelapp.data.remote.retrofit.datasource

import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.data.remote.retrofit.service.ApiServicePlaces
import javax.inject.Inject

interface TopPlacesRemoteDataSource {
	suspend fun getTopPlaces(devicetoken: String, lat: Double, lon: Double): BackendResponse
}

class TopPlacesRemoteDataSourceImpl @Inject constructor(private val apiServicePlaces: ApiServicePlaces) :
	TopPlacesRemoteDataSource {
	override suspend fun getTopPlaces(
		devicetoken: String,
		lat: Double,
		lon: Double,
	): BackendResponse {
		return apiServicePlaces.getTopPlace(devicetoken, lat, lon)
	}


}