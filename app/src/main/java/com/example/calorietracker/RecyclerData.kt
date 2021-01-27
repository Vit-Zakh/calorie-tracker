package com.example.calorietracker

sealed class RecyclerData {

    data class Meal(
        val id: Int,
        val mealName: String,
        val imageUrl: String,
        val mealWeight: Float = 0f,
        val mealCalories: Float,
        val protein: Float? = null,
        val fat: Float? = null,
        val carbs: Float? = null
    ) : RecyclerData()

    data class User(
        val id: Int,
        val userName: String,
        val userImage: String,
        val userWeight: Float,
        val userIntake: Float
    ) : RecyclerData()

    data class Food(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val calories: Float,
        val protein: Float? = null,
        val fat: Float? = null,
        val carbs: Float? = null
    ) : RecyclerData()

    object TextLine : RecyclerData()
}
