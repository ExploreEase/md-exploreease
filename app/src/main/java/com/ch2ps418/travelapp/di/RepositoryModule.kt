package com.ch2ps418.travelapp.di

import com.ch2ps418.travelapp.data.remote.repository.NearestPlacesRepository
import com.ch2ps418.travelapp.data.remote.repository.NearestPlacesRepositoryImpl
import com.ch2ps418.travelapp.data.remote.repository.SearchPlacesRepository
import com.ch2ps418.travelapp.data.remote.repository.SearchPlacesRepositoryImpl
import com.ch2ps418.travelapp.data.remote.repository.TopPlacesRepository
import com.ch2ps418.travelapp.data.remote.repository.TopPlacesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindsNearestPlacesRepository(nearestPlacesRepositoryImpl: NearestPlacesRepositoryImpl): NearestPlacesRepository

	@Binds
	abstract fun bindsSearchPlacesRepository(searchPlacesRepositoryImpl: SearchPlacesRepositoryImpl): SearchPlacesRepository

	@Binds
	abstract fun bindsTopPlacesRepository(topPlacesRepositoryImpl: TopPlacesRepositoryImpl): TopPlacesRepository

}