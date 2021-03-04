package com.example.calorietracker.state

import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Error
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSource @Inject constructor() {

    data class UserState(
        val fetchedUSer: UserResponse = UserResponse(),
        val isLoading: Boolean = false,
        val isFailed: Boolean = false
    )

    private val _cashedUser = MutableStateFlow(UserState())

    val cashedUser: StateFlow<UserState> = _cashedUser

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

    fun increaseCalories(meal: DailyIntakeProps.MealProps) {
        val progressedUser = _cashedUser.value.fetchedUSer
        progressedUser.currentIntake =
            _cashedUser.value.fetchedUSer.currentIntake.plus(meal.mealCalories * meal.mealWeight / 100)
        _cashedUser.value = _cashedUser.value.copy(fetchedUSer = progressedUser, isLoading = false, isFailed = false)
    }

    fun decreaseCalories(meal: DailyIntakeProps.MealProps) {
        val progressedUser = _cashedUser.value.fetchedUSer
        progressedUser.currentIntake =
            _cashedUser.value.fetchedUSer.currentIntake.minus(meal.mealCalories * meal.mealWeight / 100)
        _cashedUser.value = _cashedUser.value.copy(fetchedUSer = progressedUser, isLoading = false, isFailed = false)
    }
}
