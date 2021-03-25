package com.example.calorietracker.redux.store


import com.example.calorietracker.redux.actions.ReduxAction
import com.example.calorietracker.redux.states.AppState

class AppStore(var initialState: AppState): Store {

    override fun dispatch(action: ReduxAction) {
        initialState = initialState.reduce(action)
    }

    override fun subscribe(listener: StateChangeListener) {
        TODO("Not yet implemented")
    }

    override fun unsubscribe(listener: StateChangeListener) {
        TODO("Not yet implemented")
    }

}