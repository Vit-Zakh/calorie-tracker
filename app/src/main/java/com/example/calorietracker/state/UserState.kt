package com.example.calorietracker.state

import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.utils.Operations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserState @Inject constructor() {

    private val _cashedUser = MutableStateFlow(UserResponse())

    val cashedUser: StateFlow<UserResponse> = _cashedUser

    fun refreshUser(user: UserResponse) {
        _cashedUser.value = user
    }

    fun changeProgress(meal: DailyIntakeProps.MealProps, operation: Operations) {
        val progressedUser = _cashedUser.value
        when (operation) {
            Operations.ADDITION ->
                progressedUser.currentIntake =
                    _cashedUser.value.currentIntake.plus(meal.mealCalories * meal.mealWeight / 100)
            Operations.SUBTRACTION ->
                progressedUser.currentIntake =
                    _cashedUser.value.currentIntake.minus(meal.mealCalories * meal.mealWeight / 100)
        }
        _cashedUser.value = progressedUser
    }

//    fun handleApiCallError(error: String)
}
