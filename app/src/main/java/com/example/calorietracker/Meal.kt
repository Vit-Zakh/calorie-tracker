package com.example.calorietracker

data class Meal(
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

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}
