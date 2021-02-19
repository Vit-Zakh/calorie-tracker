package com.example.calorietracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

sealed class UiModel {

    @Parcelize
    data class Meal(
        val id: String,
        @SerializedName("name")
        val mealName: String,
        @SerializedName("url")
        val imageUrl: String,
        @SerializedName("weight")
        var mealWeight: Float = 0f,
        @SerializedName("calories")
        var mealCalories: Float,
        var protein: Float? = null,
        var fat: Float? = null,
        var carbs: Float? = null
    ) : UiModel(), Parcelable

    @Parcelize
    data class User(
        var id: String = "-1",
        @SerializedName("name")
        var userName: String = "Loading name",
        @SerializedName("image")
        var userImage: String = "",
        @SerializedName("weight")
        var userWeight: Float = 0f,
        @SerializedName("currentIntake")
        var userIntake: Double = 0.0,
        @SerializedName("maxIntake")
        var plannedIntake: Float = 0f
    ) : UiModel(), Parcelable

    @Parcelize
    data class Food(
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val imageUrl: String,
        val calories: Float,
        val protein: Float? = null,
        val fat: Float? = null,
        val carbs: Float? = null
    ) : UiModel(), Parcelable

    object TextLine : UiModel()
}
