package com.example.calorietracker.network

import retrofit2.http.GET

interface TrackerApiService {
    @GET("user")
    suspend fun getUser(): UserResponse

    @GET("meals")
    suspend fun getMeals(): MealsListResponse

    @GET("foodList")
    suspend fun getFoodList(): FoodResponse
}
