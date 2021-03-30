package com.example.calorietracker.userprofile

import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

interface UserProfileRepository {

    val user: StateFlow<UserDataSource.UserState>
}
