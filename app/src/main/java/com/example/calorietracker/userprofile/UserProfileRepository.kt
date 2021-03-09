package com.example.calorietracker.userprofile

import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

interface UserProfileRepository {

    val user: StateFlow<UserDataSource.UserState>

    fun saveToSharedPreferences(userName: String, userWeight: String, userAge: String, userIncome: String, userImageUrl: String, userBackgroundUrl: String)
}
