package com.example.calorietracker.cache

import android.util.Log
import com.example.calorietracker.network.MealsListResponse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsState @Inject constructor() {
    private val _cashedMealsList = MutableStateFlow(MealsListResponse())

    val cashedMealsList: MutableStateFlow<MealsListResponse> = _cashedMealsList

    fun refreshMealsList(meals: MealsListResponse) {
        _cashedMealsList.value = meals
        Log.d("TAG", "refreshMealsList: ${meals.meals.size}")
    }
}
