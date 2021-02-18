package com.example.calorietracker.network

import com.example.calorietracker.data.RecyclerData

data class FoodListResponse(
    val food: List<Food> = listOf()
)

data class Food(
    val calories: String,
    val id: String,
    val name: String,
    val url: String
)

fun FoodListResponse.mapToUiModel(): List<RecyclerData.Food> {
    val mappedFoodList = mutableListOf<RecyclerData.Food>()
    this.food.forEach {
        mappedFoodList.add(
            RecyclerData.Food(
                id = it.id,
                name = it.name,
                imageUrl = it.url,
                calories = it.calories.toFloat()
            )
        )
    }
    return mappedFoodList
}
