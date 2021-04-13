package com.example.calorietracker.graphqltest.locations.basic

import com.example.calorietracker.GetLocationDataQuery
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingLocations(val data: GetLocationDataQuery.Locations) : ReduxAction
