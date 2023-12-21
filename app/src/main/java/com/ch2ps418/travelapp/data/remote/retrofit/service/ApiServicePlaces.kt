package com.ch2ps418.travelapp.data.remote.retrofit.service

import com.ch2ps418.travelapp.data.remote.retrofit.model.BackendResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicePlaces {

	@GET("ml/nearby_treasure")
	suspend fun getTenNearestPlaces(
		@Query("devicetoken") devicetoken: String,
		@Query("lat") lat: Double,
		@Query("lon") lon: Double
	): BackendResponse

	@GET("ml/search")
	suspend fun getSearchPlace(
		@Query("devicetoken") devicetoken: String,
		@Query("placename") lat: String,
	): BackendResponse
}