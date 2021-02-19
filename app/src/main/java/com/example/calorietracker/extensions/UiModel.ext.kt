package com.example.calorietracker.extensions

import android.os.Build
import com.example.calorietracker.models.DomainModel
import com.example.calorietracker.models.UiModel
import java.lang.RuntimeException
import java.time.LocalDateTime

fun UiModel.Food.mapToMeal(weight: Float): UiModel.Meal {
    return UiModel.Meal(
        id = this.id,
        mealName = this.name,
        imageUrl = this.imageUrl,
        mealCalories = this.calories.times(weight).div(100),
        mealWeight = weight
    )
}

fun UiModel.Meal.getConvertedWeight(): String {
    return if (this.mealWeight.div(1000) >= 1) {
        "${this.mealWeight.div(1000)} kg"
    } else "${this.mealWeight} g"
}

fun UiModel.Meal.getIntakeCaloriesRounded(): String {
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

fun UiModel.Food.mapToDomainModel(): DomainModel.Food {
    return DomainModel.Food(
        id = this.id,
        name = this.name,
        url = this.imageUrl,
        calories = this.calories.toString()
    )
}

fun UiModel.Meal.mapToDomainModel(): DomainModel.Meal {
    return DomainModel.Meal(
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
