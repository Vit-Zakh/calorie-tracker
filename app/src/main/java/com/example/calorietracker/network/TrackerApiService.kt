package com.example.calorietracker.network

import com.example.calorietracker.data.RecyclerData
import retrofit2.http.GET

interface TrackerApiService {
    @GET("user")
    suspend fun getUser(): RecyclerData.User

    @GET("meals")
    suspend fun getMeal(): List<RecyclerData.Meal>

    @GET("foodList")
    suspend fun getFoodList(): List<RecyclerData.Food>
}
