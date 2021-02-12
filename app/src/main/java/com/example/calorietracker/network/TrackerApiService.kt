package com.example.calorietracker.network

import com.example.calorietracker.data.RecyclerData
import retrofit2.http.GET

interface TrackerApiService {
    @GET("user")
    suspend fun getUser(): RecyclerData.User

    @GET("meals")
    suspend fun getMeals(): MealsListResponse

    @GET("foodList")
    suspend fun getFoodList(): FoodListResponse
}
