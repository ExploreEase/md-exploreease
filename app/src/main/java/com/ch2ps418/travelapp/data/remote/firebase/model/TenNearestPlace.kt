package com.ch2ps418.travelapp.data.remote.firebase.model

import java.io.Serializable

data class TenNearestPlace(
    val Place_Id: Int,
    val Place_Name: String,
    val Category: String,
    val City: String,
    val Lat: Double,
    val Long: Double,
    val Price: Int,
    val Rating: Double
) : Serializable