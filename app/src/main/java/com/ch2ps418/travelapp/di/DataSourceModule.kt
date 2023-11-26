package com.ch2ps418.travelapp.di

import com.ch2ps418.travelapp.data.remote.retrofit.datasource.NearestPlacesRemoteDataSource
import com.ch2ps418.travelapp.data.remote.retrofit.datasource.NearestPlacesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

	@Binds
	abstract fun provideNearestPlacesRemoteDataSource(nearestPlacesRemoteDataSource: NearestPlacesRemoteDataSourceImpl): NearestPlacesRemoteDataSource
}