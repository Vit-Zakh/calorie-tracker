package com.example.calorietracker.userprofile

import android.content.SharedPreferences
import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

class UserProfileRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val sharedPreferences: SharedPreferences
) : UserProfileRepository {
    override val user: StateFlow<UserDataSource.UserState> = userDataSource.userFlow

    override fun saveToSharedPreferences(userName: String, userWeight: String, userAge: String, userIncome: String, userImageUrl: String, userBackgroundUrl: String) {
        sharedPreferences.edit()
            .putString("USER_NAME", userName)
            .putString("USER_WEIGHT", userWeight)
            .putString("USER_AGE", userAge)
            .putString("USER_INCOME", userIncome)
            .putString("USER_IMAGE_URL", userImageUrl)
            .putString("USER_BACKGROUND_URL", userBackgroundUrl)
            .apply()
        userDataSource.setLoadingState()
        userDataSource.loadCachedUser()
    }
}
