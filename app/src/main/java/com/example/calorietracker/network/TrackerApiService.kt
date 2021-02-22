package com.example.calorietracker.network

import com.example.calorietracker.models.network.FoodListResponse
import com.example.calorietracker.models.network.MealsListResponse
import com.example.calorietracker.models.network.UserResponse
import retrofit2.http.GET

interface TrackerApiService {
    @GET("user")
    suspend fun getUser(): UserResponse

    @GET("meals")
    suspend fun getMeals(): MealsListResponse

    @GET("foodList")
    suspend fun getFoodList(): FoodListResponse
}
