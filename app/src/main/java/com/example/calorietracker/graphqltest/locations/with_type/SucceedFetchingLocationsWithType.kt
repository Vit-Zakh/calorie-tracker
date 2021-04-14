package com.example.calorietracker.graphqltest.locations.with_type

import com.example.calorietracker.JointLocationsQuery
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingLocationsWithType(
    val data:
//                                       GetLocationDataWithTypeQuery.Locations?) : ReduxAction
        JointLocationsQuery.LocationsWithType?
) : ReduxAction
