package com.example.calorietracker.redux.middleware

import com.example.calorietracker.models.network.FoodListResponse
import com.example.calorietracker.models.network.MealsListResponse
import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.network.NetworkResponse
import com.example.calorietracker.network.NetworkResponseAdapterFactory
import com.example.calorietracker.redux.actions.*
import com.example.calorietracker.redux.store.AppStore
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkMiddleware(val store: AppStore) : ReduxMiddleware {

    private val apiService = Retrofit.Builder()
        .baseUrl("https://calories-tracker.free.beeceptor.com/")
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(ApiService::class.java)

    override fun apply(action: ReduxAction) {
        when (action) {
            is FetchInitialData -> {
                CoroutineScope(Dispatchers.IO).launch {
                    refreshMealsList(apiService.getMeals())
                }

                CoroutineScope(Dispatchers.IO).launch {
                    refreshUser(apiService.getUser())
                }

                CoroutineScope(Dispatchers.IO).launch {
                    refreshFoodList(apiService.getFoodList())
                }
            }
            else -> {
                action
            }
        }
    }

    private fun refreshUser(user: NetworkResponse<UserResponse, Error>) {
        when (user) {
            is NetworkResponse.Success -> {
                store.dispatch(SucceedFetchingUser(user = user.body))
            }
            else -> {
                store.dispatch(
                    FailFetchingUser(error = Error("Cannot fetch user data"))
                )
            }
        }
    }

    private fun refreshMealsList(meals: NetworkResponse<MealsListResponse, java.lang.Error>) {
        when (meals) {
            is NetworkResponse.Success ->
                store.dispatch(SucceedFetchingMeals(meals = meals.body.meals))
            else -> {
                store.dispatch(FailFetchingMeals(error = Error("Cannot fetch meals data")))
            }
        }
    }

    private fun refreshFoodList(foods: NetworkResponse<FoodListResponse, java.lang.Error>) {
        when (foods) {
            is NetworkResponse.Success ->
                store.dispatch(SucceedFetchingFood(foodList = foods.body.food))
            else -> {
                store.dispatch(FailFetchingFood(error = Error("Cannot fetch food data")))
            }
        }
    }
}
