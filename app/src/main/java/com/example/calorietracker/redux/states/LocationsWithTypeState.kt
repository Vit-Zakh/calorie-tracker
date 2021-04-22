package com.example.calorietracker.redux.states

import com.example.calorietracker.graphqltest.locations.actions.FetchLocationsDataWithType
import com.example.calorietracker.graphqltest.locations.actions.SucceedFetchingLocationsWithType
import com.example.calorietracker.graphqltest.locations.models.LocationModel
import com.example.calorietracker.redux.actions.ReduxAction

data class LocationsWithTypeState(
    val locationsList: List<LocationModel> = emptyList(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
) {

    fun reduce(action: ReduxAction): LocationsWithTypeState {
        return when (action) {
            is FetchLocationsDataWithType -> this.copy(isLoading = true)
            is SucceedFetchingLocationsWithType -> this.copy(
                locationsList = action.data,
                isLoading = false,
                isFailed = false,
            )
            else -> this
        }
    }
}
