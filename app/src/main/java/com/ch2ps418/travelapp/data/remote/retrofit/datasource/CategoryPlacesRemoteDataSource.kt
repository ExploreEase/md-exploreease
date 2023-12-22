package com.ch2ps418.travelapp.data.remote.retrofit.datasource

import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import com.ch2ps418.travelapp.data.remote.retrofit.service.ApiServicePlaces
import javax.inject.Inject

interface CategoryPlacesRemoteDataSource {
	suspend fun getSearchByCategory(devicetoken: String, lat: Double, lon: Double, usercategory: String): BackendResponse
}

class CategoryPlacesRemoteDataSourceImpl @Inject constructor(private val apiServicePlaces: ApiServicePlaces) :
	CategoryPlacesRemoteDataSource {
	override suspend fun getSearchByCategory(
		devicetoken: String,
		lat: Double,
		lon: Double,
		usercategory: String
	): BackendResponse {
		return apiServicePlaces.getSearchByCategory(devicetoken, lat, lon, usercategory)
	}


}