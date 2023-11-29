package com.ch2ps418.travelapp.data.remote.firebase.model

data class TenNearestPlaces(
    val notification: Notification,
    val tenNearestPlace: List<TenNearestPlace>
)