package com.ch2ps418.travelapp.data.remote.firebase.model

data class Places(
    val notification: Notification,
    val tenNearestPlace: List<Place>
)