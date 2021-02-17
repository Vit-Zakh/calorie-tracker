package com.example.calorietracker.cache

import com.example.calorietracker.data.RecyclerData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListState @Inject constructor() {
    var cachedFoodList: MutableList<RecyclerData.Food>? = null

//    var cachedFoodListFlow: Flow<List<RecyclerData.Food>> =
//        flow {
//            while (true)
//                cachedFoodList?.let { emit(it.toMutableList()) }
//        }
}
