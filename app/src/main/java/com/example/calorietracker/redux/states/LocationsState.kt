package com.example.calorietracker.redux.states

import com.example.calorietracker.GetLocationDataQuery
import com.example.calorietracker.graphqltest.locations.basic.StartFetchingLocations
import com.example.calorietracker.graphqltest.locations.basic.SucceedFetchingLocations
import com.example.calorietracker.redux.actions.ReduxAction

data class LocationsState(
    val locationsList: List<GetLocationDataQuery.Result?>? = null,
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
) {

    fun reduce(action: ReduxAction): LocationsState {
        return when (action) {
            is StartFetchingLocations -> this.copy(isLoading = true)
            is SucceedFetchingLocations -> this.copy(
                locationsList = action.data.results,
                isLoading = false,
                isFailed = false,
            )
            else -> this
        }
    }
}
