package com.example.calorietracker.redux.states

import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.redux.actions.*

data class UserState(
    val userData: UserResponse = UserResponse(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false
) {

    fun reduce(action: ReduxAction): UserState {
        return when (action) {
            is StartFetchingUser -> this.copy(isLoading = true, isFailed = false)
            is FailFetchingUser -> this.copy(isFailed = true, isLoading = false)
            is SucceedFetchingUser -> this.copy(
                userData = action.user,
                isLoading = false,
                isFailed = false
            )
            is FetchInitialData -> this.copy(isLoading = true, isFailed = false)
            else -> this
        }
    }
}
