package com.example.calorietracker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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

    @Parcelize
    data class User(
        val id: Int,
        val userName: String?,
        val userImage: String?,
        var userWeight: Float,
        var userIntake: Float,
        var plannedIntake: Float
    ) : RecyclerData(), Parcelable

    @Parcelize
    data class Food(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val calories: Float,
        val protein: Float? = null,
        val fat: Float? = null,
        val carbs: Float? = null
    ) : RecyclerData(), Parcelable

    object TextLine : RecyclerData()
}
