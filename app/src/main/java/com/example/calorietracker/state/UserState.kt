package com.example.calorietracker.state

import com.example.calorietracker.network.UserResponse
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
}
