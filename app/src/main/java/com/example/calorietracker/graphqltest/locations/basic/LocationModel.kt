package com.example.calorietracker.graphqltest.locations.basic

import com.example.calorietracker.GetLocationDataQuery
import com.example.calorietracker.GetLocationDataWithCreatedQuery
import com.example.calorietracker.GetLocationDataWithTypeQuery

data class LocationModel(
    val id: String,
    val name: String = "",
    val dimension: String = "",
    val created: String? = null,
    val type: String? = null
)

fun GetLocationDataWithCreatedQuery.Result.mapToBusinessModel(): LocationModel {
    return LocationModel(
        id = this.fragments.locationData.id.toString(),
        name = this.fragments.locationData.name.toString(),
        dimension = this.fragments.locationData.dimension.toString(),
        created = this.created.toString()
    )
}

fun GetLocationDataWithTypeQuery.Result.mapToBusinessModel(): LocationModel {
    return LocationModel(
        id = this.fragments.locationData.id.toString(),
        name = this.fragments.locationData.name.toString(),
        dimension = this.fragments.locationData.dimension.toString(),
        type = this.type.toString()
    )
}

fun GetLocationDataQuery.Result.mapToBusinessModel(): LocationModel {
    return LocationModel(
        id = this.fragments.locationData.id.toString(),
        name = this.fragments.locationData.name.toString(),
        dimension = this.fragments.locationData.dimension.toString(),
    )
}
