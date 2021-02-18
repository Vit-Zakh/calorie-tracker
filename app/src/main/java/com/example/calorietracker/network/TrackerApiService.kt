package com.example.calorietracker.network

import retrofit2.http.GET

interface TrackerApiService {
    @GET("user")
    suspend fun getUser(): UserResponse
//    suspend fun getUser(): RecyclerData.User

    @GET("meals")
    suspend fun getMeals(): MealsListResponse
//    suspend fun getMeals(): List<RecyclerData.Meal>

    @GET("foodList")
    suspend fun getFoodList(): FoodResponse
}
