package com.example.calorietracker.graphqltest.locations.models

import com.example.calorietracker.JointLocationsQuery

data class LocationModel(
    val id: String,
    val name: String = "",
    val dimension: String = "",
    val created: String? = null,
    val type: String? = null
)

fun JointLocationsQuery.LocationsWithCreated?.mapToBusinessModel(): List<LocationModel> {
    val list = mutableListOf<LocationModel>()
    this?.results?.forEach {
        list.add(
            LocationModel(
                id = it?.fragments?.locationData?.id.toString(),
                name = it?.fragments?.locationData?.name.toString(),
                dimension = it?.fragments?.locationData?.dimension.toString(),
                created = it?.created
            )
        )
    }
    return list
}

fun JointLocationsQuery.LocationsWithType?.mapToBusinessModel(): List<LocationModel> {
    val list = mutableListOf<LocationModel>()
    this?.results?.forEach {
        list.add(
            LocationModel(
                id = it?.fragments?.locationData?.id.toString(),
                name = it?.fragments?.locationData?.name.toString(),
                dimension = it?.fragments?.locationData?.dimension.toString(),
                type = it?.type
            )
        )
    }
    return list
}

fun JointLocationsQuery.RawLocations?.mapToBusinessModel(): List<LocationModel> {
    val list = mutableListOf<LocationModel>()
    this?.results?.forEach {
        list.add(
            LocationModel(
                id = it?.fragments?.locationData?.id.toString(),
                name = it?.fragments?.locationData?.name.toString(),
                dimension = it?.fragments?.locationData?.dimension.toString()
            )
        )
    }
    return list
}
