package com.ch2ps418.travelapp.data.remote.retrofit.datasource

import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.data.remote.retrofit.service.ApiServiceNearestPlaces
import javax.inject.Inject

interface NearestPlacesRemoteDataSource {
	suspend fun getNearestPlaces(devicetoken: String, lat: Double, lon: Double): BackendResponse
}

class NearestPlacesRemoteDataSourceImpl @Inject constructor(private val apiServiceNearestPlaces: ApiServiceNearestPlaces) :
	NearestPlacesRemoteDataSource {
	override suspend fun getNearestPlaces(
		devicetoken: String,
		lat: Double,
		lon: Double,
	): BackendResponse {
		return apiServiceNearestPlaces.getTenNearestPlaces(devicetoken, lat, lon)
	}


}