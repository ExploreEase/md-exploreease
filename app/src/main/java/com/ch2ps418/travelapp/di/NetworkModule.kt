package com.ch2ps418.travelapp.di

import com.ch2ps418.travelapp.data.remote.retrofit.service.ApiServicePlaces
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	private const val baseUrl =
		"https://capstone-backend-rtfpcq2a4a-et.a.run.app/"

	@Singleton
	@Provides
	fun provideRetrofit(): Retrofit {
		val loggingInterceptor = HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BODY)
		val client = OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()
		return Retrofit.Builder()
			.baseUrl(baseUrl)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()
	}

	@Singleton
	@Provides
	fun provideApiServicePlaces(retrofit: Retrofit): ApiServicePlaces =
		retrofit.create(ApiServicePlaces::class.java)
}
