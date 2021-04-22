package com.example.calorietracker.graphqltest.locations.actions

import com.example.calorietracker.graphqltest.locations.models.LocationModel
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingLocations(val data: List<LocationModel>) : ReduxAction