package com.example.calorietracker.network

import com.example.calorietracker.models.DomainModel

data class MealsListResponse(
    val meals: List<DomainModel.Meal> = listOf()
)
