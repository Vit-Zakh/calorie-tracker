package com.example.calorietracker.graphqltest.locations.models

data class LocationProps(
    val id: String,
    val name: String = "",
    val dimension: String = "",
    val created: String? = null,
    val type: String? = null
)

fun LocationModel.mapToUiModel() = LocationProps(
    id = this.id,
    name = this.name,
    dimension = this.dimension,
    created = this.created,
    type = this.type
)
