package com.example.calorietracker.redux.store

import com.example.calorietracker.redux.actions.ReduxAction
import com.example.calorietracker.redux.states.AppState

interface Store {
    fun dispatch(action: ReduxAction)
    fun subscribe(listener: StateChangeListener)
    fun unsubscribe(listener: StateChangeListener)
}

interface StateChangeListener {
    fun onUpdate(state: AppState)
}