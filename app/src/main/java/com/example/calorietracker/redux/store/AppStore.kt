package com.example.calorietracker.redux.store

import com.example.calorietracker.redux.actions.ReduxAction
import com.example.calorietracker.redux.states.AppState

class AppStore(var initialState: AppState) : Store {

    private val listeners = mutableListOf<StateChangeListener>()

    override fun dispatch(action: ReduxAction) {
        initialState = initialState.reduce(action)
        listeners.forEach { it.onUpdate(state = initialState) }
    }

    override fun subscribe(listener: StateChangeListener) {
        listeners.add(listener)
    }

    override fun unsubscribe(listener: StateChangeListener) {
        listeners.remove(listener)
    }
}
