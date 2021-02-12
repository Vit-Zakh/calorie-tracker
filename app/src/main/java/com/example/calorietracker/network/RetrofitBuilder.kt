package com.example.calorietracker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL: String = "https://calories-tracker.free.beeceptor.com/"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val trackerApiService: TrackerApiService by lazy {
        return@lazy retrofitBuilder.build().create(TrackerApiService::class.java)
    }
}
