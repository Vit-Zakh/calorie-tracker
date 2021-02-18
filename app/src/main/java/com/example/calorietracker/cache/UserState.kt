package com.example.calorietracker.cache

import com.example.calorietracker.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserState @Inject constructor() {
    private val _cashedUser = MutableStateFlow(UserResponse())

    val cashedUser: MutableStateFlow<UserResponse> = _cashedUser

    fun refreshUser(user: UserResponse) {
        _cashedUser.value = user
    }
}
