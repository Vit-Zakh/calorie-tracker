package com.example.calorietracker.state

import android.content.SharedPreferences
import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) {

    data class UserState(
        val fetchedUser: UserResponse = UserResponse(),
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
            is NetworkResponse.Success -> _userFlow.value = _userFlow.value.copy(fetchedUser = user.body, isLoading = false)
            else -> {
                _userFlow.value = _userFlow.value.copy(isLoading = false, isFailed = true)
            }
        }
    }

    fun loadCachedUser() {
        _userFlow.value = UserState(
            UserResponse(
                id = "cachedUser",
                name = sharedPreferences.getString("USER_NAME", ""),
                weight = sharedPreferences.getString("USER_WEIGHT", "")?.toFloat(),
                maxIntake = sharedPreferences.getString("USER_INCOME", "")?.toFloat(),
                image = sharedPreferences.getString("USER_IMAGE_URL", ""),
                backgroundImage = sharedPreferences.getString("USER_BACKGROUND_URL", ""),
                age = sharedPreferences.getString("USER_AGE", null)?.toInt(),
            ),
            isLoading = false,
            isFailed = false
        )
    }

    fun increaseCalories(meal: DailyIntakeProps.MealProps) {
        val progressedUser = _userFlow.value.fetchedUser
        progressedUser.currentIntake =
            _userFlow.value.fetchedUser.currentIntake.plus(meal.mealCalories * meal.mealWeight / 100)
        _userFlow.value = _userFlow.value.copy(fetchedUser = progressedUser, isLoading = false, isFailed = false)
    }

    fun decreaseCalories(meal: DailyIntakeProps.MealProps) {
        val progressedUser = _userFlow.value.fetchedUser
        progressedUser.currentIntake =
            _userFlow.value.fetchedUser.currentIntake.minus(meal.mealCalories * meal.mealWeight / 100)
        _userFlow.value = _userFlow.value.copy(fetchedUser = progressedUser, isLoading = false, isFailed = false)
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
