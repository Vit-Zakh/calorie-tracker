package com.example.calorietracker.network

import com.example.calorietracker.data.RecyclerData

data class UserResponse(
    val id: String,
    val image: String,
    val name: String,
    val currentIntake: Double,
    val maxIntake: Float,
    val weight: Float

)

fun UserResponse.mapToBusinessModel(): RecyclerData.User {
    return RecyclerData.User(
        id = this.id,
        userName = this.name,
        userImage = this.image,
        userWeight = this.weight,
        userIntake = this.currentIntake,
        plannedIntake = this.maxIntake
    )
}
