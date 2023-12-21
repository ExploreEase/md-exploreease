package com.ch2ps418.travelapp.data.remote.retrofit.datasource

import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.data.remote.retrofit.service.ApiServicePlaces
import javax.inject.Inject

interface NearestPlacesRemoteDataSource {
	suspend fun getNearestPlaces(devicetoken: String, lat: Double, lon: Double): BackendResponse
}

class NearestPlacesRemoteDataSourceImpl @Inject constructor(private val apiServicePlaces: ApiServicePlaces) :
	NearestPlacesRemoteDataSource {
	override suspend fun getNearestPlaces(
		devicetoken: String,
		lat: Double,
		lon: Double,
	): BackendResponse {
		return apiServicePlaces.getTenNearestPlaces(devicetoken, lat, lon)
	}


}