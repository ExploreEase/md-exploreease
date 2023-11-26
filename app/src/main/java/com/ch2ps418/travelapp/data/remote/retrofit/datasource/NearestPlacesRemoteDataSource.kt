package com.ch2ps418.travelapp.data.remote.retrofit.datasource

import com.ch2ps418.travelapp.data.remote.retrofit.model.NearestPlacesResponse
import com.ch2ps418.travelapp.data.remote.retrofit.service.ApiServiceNearestPlaces
import javax.inject.Inject

interface NearestPlacesRemoteDataSource {
	suspend fun getNearestPlaces(devicetoken: String, lat: Double, lon: Double): NearestPlacesResponse
}

class NearestPlacesRemoteDataSourceImpl @Inject constructor(private val apiServiceNearestPlaces: ApiServiceNearestPlaces) :
	NearestPlacesRemoteDataSource {
	override suspend fun getNearestPlaces(
		devicetoken: String,
		lat: Double,
		lon: Double,
	): NearestPlacesResponse {
		return apiServiceNearestPlaces.getTenNearestPlaces(devicetoken, lat, lon)
	}


}