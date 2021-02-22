package com.example.calorietracker.models

abstract class DomainModel {

    data class Meal(
        val calories: String,
        val date: String,
        val id: String,
        val name: String,
        val url: String,
        val weight: String
    ) : DomainModel()

    data class Food(
        val calories: String,
        val id: String,
        val name: String,
        val url: String
    ) : DomainModel()
}
