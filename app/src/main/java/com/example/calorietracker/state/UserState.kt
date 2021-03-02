package com.example.calorietracker.state

import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.network.NetworkResponse
import com.example.calorietracker.utils.Operations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Error
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserState @Inject constructor() {

    data class FetchedUserState(
        val fetchedUSer: UserResponse = UserResponse(),
        val isLoading: Boolean = false,
        val isFailed: Boolean = false
    )

    private val _cashedUser = MutableStateFlow(FetchedUserState())

    val cashedUser: StateFlow<FetchedUserState> = _cashedUser

    fun startFetching() {
        _cashedUser.value = _cashedUser.value.copy(isLoading = true)
    }

    fun refreshUser(user: NetworkResponse<UserResponse, Error>) {
        when (user) {
            is NetworkResponse.Success -> _cashedUser.value = _cashedUser.value.copy(fetchedUSer = user.body, isLoading = false)
            else -> {
                _cashedUser.value = _cashedUser.value.copy(isLoading = false, isFailed = true)
            }
        }
    }

    fun changeProgress(meal: DailyIntakeProps.MealProps, operation: Operations) {
        val progressedUser = _cashedUser.value.fetchedUSer
        when (operation) {
            Operations.ADDITION ->
                progressedUser.currentIntake =
                    _cashedUser.value.fetchedUSer.currentIntake.plus(meal.mealCalories * meal.mealWeight / 100)
            Operations.SUBTRACTION ->
                progressedUser.currentIntake =
                    _cashedUser.value.fetchedUSer.currentIntake.minus(meal.mealCalories * meal.mealWeight / 100)
        }
        _cashedUser.value = _cashedUser.value.copy(fetchedUSer = progressedUser, isLoading = false, isFailed = false)
    }
}
