package com.example.calorietracker.redux.states

import com.example.calorietracker.graphqltest.locations.actions.FetchLocationsDataWithCreated
import com.example.calorietracker.graphqltest.locations.actions.SucceedFetchingLocationsWithCreated
import com.example.calorietracker.graphqltest.locations.models.LocationModel
import com.example.calorietracker.redux.actions.ReduxAction

data class LocationsWithCreatedState(
    val locationsList: List<LocationModel> = emptyList(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
) {

    fun reduce(action: ReduxAction): LocationsWithCreatedState {
        return when (action) {
            is FetchLocationsDataWithCreated -> this.copy(isLoading = true)
            is SucceedFetchingLocationsWithCreated -> this.copy(
                locationsList = action.data,
                isLoading = false,
                isFailed = false,
            )
            else -> this
        }
    }
}
