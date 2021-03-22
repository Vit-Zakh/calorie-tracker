package com.example.calorietracker.models.network

import com.example.calorietracker.models.ui.DailyIntakeProps

data class UserResponse(
    val id: String = "",
    var image: String? = "",
    var name: String? = "",
    var currentIntake: Double = 0.0,
    var maxIntake: Float? = 0f,
    var weight: Float? = 0f,
    var age: Int? = null,
    var backgroundImage: String? = "",
)

fun UserResponse.mapToUiModel(): DailyIntakeProps.UserProps {
    return DailyIntakeProps.UserProps(
        id = this.id,
        userName = this.name.toString(),
        userImage = this.image,
        userWeight = this.weight,
        userIntake = this.currentIntake,
        plannedIntake = this.maxIntake ?: 0f,
        userAge = this.age.toString(),
        backgroundImage = this.backgroundImage
    )
}
