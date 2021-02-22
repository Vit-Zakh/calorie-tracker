package com.example.calorietracker.models.network

import com.example.calorietracker.models.ui.DailyIntakeProps

data class UserResponse(
    val id: String = "-1",
    val image: String = "",
    val name: String = "Loading user",
    var currentIntake: Double = 0.0,
    val maxIntake: Float = 0f,
    val weight: Float = 0f
)

fun UserResponse.mapToUiModel(): DailyIntakeProps.UserProps {
    return DailyIntakeProps.UserProps(
        id = this.id,
        userName = this.name,
        userImage = this.image,
        userWeight = this.weight,
        userIntake = this.currentIntake,
        plannedIntake = this.maxIntake
    )
}
