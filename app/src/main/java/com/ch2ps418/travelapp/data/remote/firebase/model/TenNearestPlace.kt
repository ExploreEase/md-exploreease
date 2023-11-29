package com.ch2ps418.travelapp.data.remote.firebase.model

data class TenNearestPlace(
    val Category: String,
    val City: String,
    val Lat: Double,
    val Long: Double,
    val Place_Id: Int,
    val Place_Name: String,
    val Price: Int,
    val Rating: Double
)