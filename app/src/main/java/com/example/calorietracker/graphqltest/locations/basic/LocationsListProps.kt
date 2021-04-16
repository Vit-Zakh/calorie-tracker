package com.example.calorietracker.graphqltest.locations.basic

import com.example.calorietracker.graphqltest.locations.models.LocationProps

sealed class LocationsListProps {

    class LoadedList(val locationsList: List<LocationProps>) : LocationsListProps()
    object LoadingList : LocationsListProps()
    object EmptyList : LocationsListProps()
    object FailedList : LocationsListProps()
}
