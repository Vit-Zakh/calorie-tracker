package com.example.calorietracker.di

import com.example.calorietracker.network.ApiService
import com.example.calorietracker.network.NetworkResponseAdapterFactory
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideTrackerApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://calories-tracker.free.beeceptor.com/")
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
    }
}
