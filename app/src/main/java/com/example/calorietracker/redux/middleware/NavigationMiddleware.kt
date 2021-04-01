package com.example.calorietracker.redux.middleware

import android.util.Log
import com.example.calorietracker.MainActivity
import com.example.calorietracker.MyApplication
import com.example.calorietracker.foodlist.AddMealDialog
import com.example.calorietracker.redux.actions.ChangeScreen
import com.example.calorietracker.redux.actions.CloseDialogAction
import com.example.calorietracker.redux.actions.OpenFoodDialog
import com.example.calorietracker.redux.actions.ReduxAction
import com.example.calorietracker.redux.store.AppStore
import com.github.terrakok.modo.forward

class NavigationMiddleware(val store: AppStore) : ReduxMiddleware {
    override fun apply(action: ReduxAction) {
        when (action) {
            is ChangeScreen ->
                MyApplication.modo.forward(action.destination)
            is OpenFoodDialog ->
                AddMealDialog().show(MainActivity.myFragmentManager, "Tag")
            is CloseDialogAction -> {
                Log.d("DISMISS_TAG", "apply: this action was called")
                action.dialog.dismiss()
            }
        }
    }
}
