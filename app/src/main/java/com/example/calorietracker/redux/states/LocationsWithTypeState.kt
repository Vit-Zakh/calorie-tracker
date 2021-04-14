package com.example.calorietracker.redux.states

import com.example.calorietracker.graphqltest.locations.basic.StartFetchingLocations
import com.example.calorietracker.graphqltest.locations.models.LocationModel
import com.example.calorietracker.graphqltest.locations.with_type.SucceedFetchingLocationsWithType
import com.example.calorietracker.redux.actions.ReduxAction

data class LocationsWithTypeState(
    val locationsList: List<LocationModel> = emptyList(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
) {

    fun reduce(action: ReduxAction): LocationsWithTypeState {
        return when (action) {
            is StartFetchingLocations -> this.copy(isLoading = true)
            is SucceedFetchingLocationsWithType -> this.copy(
                locationsList = action.data,
                isLoading = false,
                isFailed = false,
            )
            else -> this
        }
    }
}
