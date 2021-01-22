package com.example.calorietracker

sealed class RecyclerData

data class Meal(
    val id: Int,
    val mealName: String,
    val imageUrl: String,
    val mealCalories: Float,
    val protein: Float?,
    val fat: Float?,
    val cards: Float?
) : RecyclerData() {
    constructor(
        id: Int,
        mealName: String,
        imageUrl: String,
        mealCalories: Float
    ) : this(id, mealName, imageUrl, mealCalories, null, null, null)
}

data class User(
    val id: Int,
    val userName: String,
    val userImage: String,
    val userWeight: Float,
    val userIntake: Float
) : RecyclerData()

object TextLine : RecyclerData()
