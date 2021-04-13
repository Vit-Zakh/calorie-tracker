package com.example.calorietracker.graphqltest.locations.basic

sealed class LocationsListProps {

    class LoadedList(val locationsList: List<LocationModel?>?) : LocationsListProps()
    object LoadingList : LocationsListProps()
    object EmptyList : LocationsListProps()
    object FailedList : LocationsListProps()
}
