package com.example.calorietracker.state

import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSource @Inject constructor() {

    data class UserState(
        val fetchedUSer: UserResponse = UserResponse(),
        val isLoading: Boolean = false,
        val isFailed: Boolean = false
    )

    private val _userFlow = MutableStateFlow(UserState())

    val userFlow: StateFlow<UserState> = _userFlow

    fun setLoadingState() {
        _userFlow.value = _userFlow.value.copy(isLoading = true)
    }

    fun refreshUser(user: NetworkResponse<UserResponse, Error>) {
        when (user) {
            is NetworkResponse.Success -> _userFlow.value = _userFlow.value.copy(fetchedUSer = user.body, isLoading = false)
            else -> {
                _userFlow.value = _userFlow.value.copy(isLoading = false, isFailed = true)
            }
        }
    }

    fun getCachedUser(userName: String?, userWeight: String?, userAge: String?, userIncome: String?, userImageUrl: String?, userBackgroundUrl: String?) {
        _userFlow.value = UserState(UserResponse("cached", userImageUrl, userName, 0.0, userIncome?.toFloat(), userWeight?.toFloat()), isLoading = false, isFailed = false)
    }

    fun increaseCalories(meal: DailyIntakeProps.MealProps) {
        val progressedUser = _userFlow.value.fetchedUSer
        progressedUser.currentIntake =
            _userFlow.value.fetchedUSer.currentIntake.plus(meal.mealCalories * meal.mealWeight / 100)
        _userFlow.value = _userFlow.value.copy(fetchedUSer = progressedUser, isLoading = false, isFailed = false)
    }

    fun decreaseCalories(meal: DailyIntakeProps.MealProps) {
        val progressedUser = _userFlow.value.fetchedUSer
        progressedUser.currentIntake =
            _userFlow.value.fetchedUSer.currentIntake.minus(meal.mealCalories * meal.mealWeight / 100)
        _userFlow.value = _userFlow.value.copy(fetchedUSer = progressedUser, isLoading = false, isFailed = false)
    }

    /** Test functions block */

    fun showFailedUser() {
        _userFlow.value = _userFlow.value.copy(isLoading = false, isFailed = true)
    }

    fun showLoadedUser() {
        _userFlow.value = _userFlow.value.copy(isLoading = false, isFailed = false)
    }

    /** End of test functions block */
}
