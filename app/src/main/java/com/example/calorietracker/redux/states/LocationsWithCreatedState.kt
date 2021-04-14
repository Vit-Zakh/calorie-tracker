package com.example.calorietracker.redux.states

import com.example.calorietracker.graphqltest.locations.basic.StartFetchingLocations
import com.example.calorietracker.graphqltest.locations.models.LocationModel
import com.example.calorietracker.graphqltest.locations.with_created.SucceedFetchingLocationsWithCreated
import com.example.calorietracker.redux.actions.ReduxAction

data class LocationsWithCreatedState(
//    val locationsList: List<GetLocationDataWithCreatedQuery.Result?>? = null,
//    val locationsList: JointLocationsQuery.LocationsWithCreated? = null,
    val locationsList: List<LocationModel> = emptyList(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
) {

    fun reduce(action: ReduxAction): LocationsWithCreatedState {
        return when (action) {
            is StartFetchingLocations -> this.copy(isLoading = true)
            is SucceedFetchingLocationsWithCreated -> this.copy(
//                locationsList = action.data?.results,
                locationsList = action.data,
                isLoading = false,
                isFailed = false,
            )
            else -> this
        }
    }
}
