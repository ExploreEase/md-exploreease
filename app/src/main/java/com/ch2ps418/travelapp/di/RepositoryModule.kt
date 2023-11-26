package com.ch2ps418.travelapp.di

import com.ch2ps418.travelapp.data.remote.repository.NearestPlacesRepository
import com.ch2ps418.travelapp.data.remote.repository.NearestPlacesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindsNearestPlacesRepository(nearestPlacesRepositoryImpl: NearestPlacesRepositoryImpl): NearestPlacesRepository

}