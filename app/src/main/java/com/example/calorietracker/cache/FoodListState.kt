package com.example.calorietracker.cache

import com.example.calorietracker.data.RecyclerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListState @Inject constructor() {
    val cachedFoodList = mutableListOf<RecyclerData.Food>()
    val foodListFlow: Flow<List<RecyclerData.Food>> = flow {
        while (true) {
            kotlinx.coroutines.delay(3000)
            emit(cachedFoodList)
        }
    }
}
