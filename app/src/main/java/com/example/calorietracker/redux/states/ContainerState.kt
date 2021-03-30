package com.example.calorietracker.redux.states

import com.example.calorietracker.redux.actions.ChangeScreen
import com.example.calorietracker.redux.actions.InitScreen
import com.example.calorietracker.redux.actions.ReduxAction
import com.github.terrakok.modo.android.AppScreen

data class ContainerState(
    val currentScreen: AppScreen
) {

    fun reduce(action: ReduxAction): ContainerState {
        return when (action) {
            is InitScreen -> this.copy(currentScreen = action.start)
            is ChangeScreen -> this.copy(currentScreen = action.destination)
            else -> this
        }
    }
}
