package com.example.calorietracker.redux.store

import com.example.calorietracker.redux.actions.ReduxAction
import com.example.calorietracker.redux.middleware.NetworkMiddleware
import com.example.calorietracker.redux.middleware.ReduxMiddleware
import com.example.calorietracker.redux.states.AppState

class AppStore(var appState: AppState) : Store {

    private val middlewares = listOf<ReduxMiddleware>(NetworkMiddleware(this))
    private val listeners = mutableListOf<StateChangeListener>()

    override fun dispatch(action: ReduxAction) {
        appState = appState.reduce(action)
        middlewares.forEach {
            it.apply(
                action = action
            )
        }
        listeners.forEach { it.onUpdate(state = appState) }
    }

    override fun subscribe(listener: StateChangeListener) {
        listeners.add(listener)
    }

    override fun unsubscribe(listener: StateChangeListener) {
        listeners.remove(listener)
    }
}