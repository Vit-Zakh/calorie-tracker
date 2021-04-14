package com.example.calorietracker.graphqltest.locations.with_type

import com.example.calorietracker.graphqltest.locations.models.LocationModel
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingLocationsWithType(
    val data:
//                                       GetLocationDataWithTypeQuery.Locations?) : ReduxAction
//        JointLocationsQuery.LocationsWithType?
        List<LocationModel>
) : ReduxAction
