package com.example.calorietracker.graphqltest.locations.basic

import com.example.calorietracker.graphqltest.locations.models.LocationModel

sealed class LocationsListProps {

//    class LoadedList(val locationsList: List<LocationModel?>?) : LocationsListProps()
    class LoadedList(val locationsList: List<LocationModel?>?) : LocationsListProps()
    object LoadingList : LocationsListProps()
    object EmptyList : LocationsListProps()
    object FailedList : LocationsListProps()
}
