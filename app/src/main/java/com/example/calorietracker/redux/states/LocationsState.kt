package com.example.calorietracker.redux.states

import com.example.calorietracker.graphqltest.locations.basic.FetchLocationsData
import com.example.calorietracker.graphqltest.locations.basic.SucceedFetchingLocations
import com.example.calorietracker.graphqltest.locations.models.LocationModel
import com.example.calorietracker.redux.actions.ReduxAction

data class LocationsState(
    val locationsList: List<LocationModel> = emptyList(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
) {

    fun reduce(action: ReduxAction): LocationsState {
        return when (action) {
            is FetchLocationsData -> this.copy(isLoading = true)
            is SucceedFetchingLocations -> this.copy(
                locationsList = action.data,
                isLoading = false,
                isFailed = false,
            )
            else -> this
        }
    }
}
