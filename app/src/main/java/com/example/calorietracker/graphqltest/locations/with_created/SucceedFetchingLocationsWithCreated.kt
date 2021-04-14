package com.example.calorietracker.graphqltest.locations.with_created

import com.example.calorietracker.JointLocationsQuery
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingLocationsWithCreated(
    val data:
//                                          GetLocationDataWithCreatedQuery.Locations?) : ReduxAction
        JointLocationsQuery.LocationsWithCreated?
) : ReduxAction
