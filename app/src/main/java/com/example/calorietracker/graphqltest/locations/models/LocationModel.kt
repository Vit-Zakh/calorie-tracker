package com.example.calorietracker.graphqltest.locations.models

import com.example.calorietracker.JointLocationsQuery

data class LocationModel(
    val id: String,
    val name: String = "",
    val dimension: String = "",
    val created: String? = null,
    val type: String? = null
)

fun JointLocationsQuery.LocationsWithCreated?.mapToBusinessModel() = this?.results?.map {
    LocationModel(
        id = it?.fragments?.locationData?.id.toString(),
        name = it?.fragments?.locationData?.name.toString(),
        dimension = it?.fragments?.locationData?.dimension.toString(),
        created = it?.created
    )
} ?: listOf()

fun JointLocationsQuery.LocationsWithType?.mapToBusinessModel() = this?.results?.map {
    LocationModel(
        id = it?.fragments?.locationData?.id.toString(),
        name = it?.fragments?.locationData?.name.toString(),
        dimension = it?.fragments?.locationData?.dimension.toString(),
        type = it?.type
    )
} ?: listOf()

fun JointLocationsQuery.RawLocations?.mapToBusinessModel() = this?.results?.map {
    LocationModel(
        id = it?.fragments?.locationData?.id.toString(),
        name = it?.fragments?.locationData?.name.toString(),
        dimension = it?.fragments?.locationData?.dimension.toString(),
    )
} ?: listOf()
