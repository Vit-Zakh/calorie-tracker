package com.example.calorietracker.models.ui

import android.os.Build
import android.os.Parcelable
import com.example.calorietracker.models.network.MealResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

sealed class DailyIntakeProps {

    @Parcelize
    data class MealProps(
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
    ) : DailyIntakeProps(), Parcelable

    class LoadedMealsList(val mealsList: List<MealProps>) : DailyIntakeProps()
    object LoadingMealsItem : DailyIntakeProps()
    object EmptyMealsItem : DailyIntakeProps()
    object FailedMealsItem : DailyIntakeProps()

    @Parcelize
    data class UserProps(
        var id: String = "-1",
        @SerializedName("name")
        var userName: String = "Loading name",
        @SerializedName("image")
        var userImage: String? = "",
        @SerializedName("weight")
        var userWeight: Float? = 0f,
        @SerializedName("currentIntake")
        var userIntake: Double = 0.0,
        @SerializedName("maxIntake")
        var plannedIntake: Float? = 0f
    ) : DailyIntakeProps(), Parcelable

    class LoadedUser(val user: UserProps) : DailyIntakeProps()
    object LoadingUser : DailyIntakeProps()
    object FailedUser : DailyIntakeProps()

    object TextLine : DailyIntakeProps()
}

fun DailyIntakeProps.MealProps.getConvertedWeight(): String {
    return if (this.mealWeight.div(1000) >= 1) {
        "${this.mealWeight.div(1000)} kg"
    } else "${this.mealWeight} g"
}

fun DailyIntakeProps.MealProps.getIntakeCaloriesRounded(): String {
    val intakeCalories = this.mealWeight.div(100f).times(this.mealCalories)
    return when {
        intakeCalories.div(10000) in 1f..10f -> {
            "%.${0}f".format(intakeCalories)
        }
        intakeCalories.div(10000) < 1 -> {
            "%.${2}f".format(intakeCalories)
        }
        else -> throw RuntimeException("Crash while converting calories")
    }
}

fun DailyIntakeProps.MealProps.mapToDomainModel(): MealResponse {
    return MealResponse(
        id = this.id,
        name = this.mealName,
        url = this.imageUrl,
        weight = mealWeight.toString(),
        calories = this.mealCalories.toString(),
        date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().toString()
        } else {
            ""
        }
    )
}
