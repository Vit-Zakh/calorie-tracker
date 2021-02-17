package com.example.calorietracker.cache

import com.example.calorietracker.data.RecyclerData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsState @Inject constructor() {
    var cachedMeals: MutableList<RecyclerData.Meal>? = null
}
