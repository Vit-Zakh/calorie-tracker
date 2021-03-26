package com.example.calorietracker.redux.middleware

import com.example.calorietracker.redux.actions.ReduxAction

interface ReduxMiddleware {
    fun apply(
        action: ReduxAction
    ): ReduxAction
}
