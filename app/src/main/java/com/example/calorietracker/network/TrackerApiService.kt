package com.example.calorietracker.network

import com.example.calorietracker.models.network.FoodListResponse
import com.example.calorietracker.models.network.MealsListResponse
import com.example.calorietracker.models.network.UserResponse
import retrofit2.http.GET
import java.lang.Error

interface TrackerApiService {

    @GET("user")
    suspend fun getUser(): NetworkResponse<UserResponse, Error>

    @GET("meals")
    suspend fun getMeals(): NetworkResponse<MealsListResponse, Error>

    @GET("foodList")
    suspend fun getFoodList(): NetworkResponse<FoodListResponse, Error>
}
