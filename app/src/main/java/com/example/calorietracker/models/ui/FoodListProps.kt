package com.example.calorietracker.models.ui

import android.os.Parcelable
import com.example.calorietracker.models.network.FoodResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

sealed class FoodListProps {

    @Parcelize
    data class FoodProps(
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val imageUrl: String,
        val calories: Float,
        val protein: Float? = null,
        val fat: Float? = null,
        val carbs: Float? = null
    ) : FoodListProps(), Parcelable

    object EmptyFood : FoodListProps()

    object ErrorFood : FoodListProps()
}

fun FoodListProps.FoodProps.mapToDomainModel(): FoodResponse {
    return FoodResponse(
        id = this.id,
        name = this.name,
        url = this.imageUrl,
        calories = this.calories.toString()
    )
}

fun FoodListProps.FoodProps.mapToMeal(weight: Float): DailyIntakeProps.MealProps {
    return DailyIntakeProps.MealProps(
        id = this.id,
        mealName = this.name,
        imageUrl = this.imageUrl,
        mealCalories = this.calories.times(weight).div(100),
        mealWeight = weight
    )
}
