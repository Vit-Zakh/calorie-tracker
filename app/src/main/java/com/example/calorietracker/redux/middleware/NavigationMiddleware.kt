package com.example.calorietracker.redux.middleware

import com.example.calorietracker.MyApplication
import com.example.calorietracker.redux.actions.ChangeScreen
import com.example.calorietracker.redux.actions.InitScreen
import com.example.calorietracker.redux.actions.ReduxAction
import com.example.calorietracker.redux.store.AppStore
import com.github.terrakok.modo.android.init
import com.github.terrakok.modo.forward

class NavigationMiddleware(val store: AppStore) : ReduxMiddleware {
    override fun apply(action: ReduxAction) {
        when (action) {
            is InitScreen -> MyApplication.modo.init(null, action.start)
            is ChangeScreen ->
                MyApplication.modo.forward(action.destination)
        }
    }
}
