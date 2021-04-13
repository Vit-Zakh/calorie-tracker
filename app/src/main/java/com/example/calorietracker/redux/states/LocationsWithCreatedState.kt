package com.example.calorietracker.redux.states

import com.example.calorietracker.GetLocationDataWithCreatedQuery
import com.example.calorietracker.graphqltest.locations.basic.StartFetchingLocations
import com.example.calorietracker.graphqltest.locations.with_created.SucceedFetchingLocationsWithCreated
import com.example.calorietracker.redux.actions.ReduxAction

data class LocationsWithCreatedState(
    val locationsList: List<GetLocationDataWithCreatedQuery.Result?>? = null,
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
) {

    fun reduce(action: ReduxAction): LocationsWithCreatedState {
        return when (action) {
            is StartFetchingLocations -> this.copy(isLoading = true)
            is SucceedFetchingLocationsWithCreated -> this.copy(
                locationsList = action.data.results,
                isLoading = false,
                isFailed = false,
            )
            else -> this
        }
    }
}
