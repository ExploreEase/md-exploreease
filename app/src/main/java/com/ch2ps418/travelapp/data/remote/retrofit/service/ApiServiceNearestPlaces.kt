package com.ch2ps418.travelapp.data.remote.retrofit.service

import com.ch2ps418.travelapp.data.remote.retrofit.model.NearestPlacesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceNearestPlaces {

	@GET("ml/tennearestplace")
	suspend fun getTenNearestPlaces(
		@Query("devicetoken") devicetoken: String,
		@Query("lat") lat: Double,
		@Query("lon") lon: Double
	): NearestPlacesResponse
}