package com.example.calorietracker.userprofile

import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

class UserProfileRepositoryImpl(
    private val userDataSource: UserDataSource,
) : UserProfileRepository {
    override val user: StateFlow<UserDataSource.UserState> = userDataSource.userFlow
}
