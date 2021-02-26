package com.example.calorietracker.models.network

import com.example.calorietracker.models.ui.FoodListProps
import com.example.calorietracker.models.ui.FoodProps

data class FoodListResponse(
    val food: MutableList<FoodResponse> = mutableListOf()
)

fun FoodResponse.mapToUiModel(): FoodProps {
    return FoodProps(
        id = id,
        name = name,
        imageUrl = url,
        calories = calories.toFloat()
    )
}

fun FoodResponse.mapToUiModel_1(): FoodListProps.FoodProps {
    return FoodListProps.FoodProps(
        id = id,
        name = name,
        imageUrl = url,
        calories = calories.toFloat()
    )
}
