package com.example.calorietracker.redux

import com.example.calorietracker.redux.actions.ReduxAction

typealias Reducer<AppState> = (AppState, ReduxAction) -> AppState
