package com.example.calorietracker.network

import com.example.calorietracker.models.UiModel

data class UserResponse(
    val id: String = "-1",
    val image: String = "",
    val name: String = "Loading user",
    val currentIntake: Double = 0.0,
    val maxIntake: Float = 0f,
    val weight: Float = 0f
)

fun UserResponse.mapToUiModel(): UiModel.User {
    return UiModel.User(
        id = this.id,
        userName = this.name,
        userImage = this.image,
        userWeight = this.weight,
        userIntake = this.currentIntake,
        plannedIntake = this.maxIntake
    )
}
