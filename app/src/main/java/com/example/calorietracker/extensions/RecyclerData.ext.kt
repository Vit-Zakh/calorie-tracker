package com.example.calorietracker.extensions

import com.example.calorietracker.data.RecyclerData
import java.lang.RuntimeException

fun RecyclerData.Food.mapToMeal(weight: Float): RecyclerData.Meal {
    return RecyclerData.Meal(
        id = this.id,
        mealName = this.name,
        imageUrl = this.imageUrl,
        mealCalories = this.calories.times(weight).div(100),
        mealWeight = weight
    )
}

fun RecyclerData.Meal.getConvertedWeight(): String {
    return if (this.mealWeight.div(1000) >= 1) {
        "${this.mealWeight.div(1000)} kg"
    } else "${this.mealWeight} g"
}

fun RecyclerData.Meal.getIntakeCaloriesRounded(): String {
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
