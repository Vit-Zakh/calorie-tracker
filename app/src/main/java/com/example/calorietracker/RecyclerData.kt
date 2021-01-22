package com.example.calorietracker

sealed class RecyclerData

data class MMeal(
    val id: Int,
    val mealName: String,
    val imageUrl: String,
    val mealCalories: Float,
    val protein: Float?,
    val fat: Float?,
    val cards: Float?
) {
    constructor(
        id: Int,
        mealName: String,
        imageUrl: String,
        mealCalories: Float
    ) : this(id, mealName, imageUrl, mealCalories, null, null, null)
}

data class MUser(
    val id: Int,
    val userName: String,
    val userImage: String,
    val userWeight: Float
)