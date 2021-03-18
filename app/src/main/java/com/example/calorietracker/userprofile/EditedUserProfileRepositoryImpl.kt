package com.example.calorietracker.userprofile

import android.content.SharedPreferences
import com.example.calorietracker.state.EditedUserDataSource
import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

class EditedUserProfileRepositoryImpl(
    private val editedUserDataSource: EditedUserDataSource,
    private val userDataSource: UserDataSource,
    private val sharedPreferences: SharedPreferences
) : EditedUserProfileRepository {
    override val editedUser: StateFlow<EditedUserDataSource.EditedUserState> = editedUserDataSource.editedUserFlow

    override fun saveToSharedPreferences(
        userName: String,
        userWeight: String,
        userAge: String,
        userIncome: String,
        userImageUrl: String,
        userBackgroundUrl: String
    ) {
        sharedPreferences.edit()
            .putString("USER_NAME", userName)
            .putString("USER_WEIGHT", userWeight)
            .putString("USER_AGE", userAge)
            .putString("USER_INCOME", userIncome)
            .putString("USER_IMAGE_URL", userImageUrl)
            .putString("USER_BACKGROUND_URL", userBackgroundUrl)
            .apply()
        editedUserDataSource.setChangingState()
    }

    override fun changeProfilePreview(uri: String) {
        editedUserDataSource.setChangingState()
        editedUserDataSource.changeProfilePreview(uri)
    }

    override fun changeBackgroundPreview(uri: String) {
        editedUserDataSource.setChangingState()
        editedUserDataSource.changeBackgroundPreview(uri)
    }

    override fun loadCachedUser() {
        editedUserDataSource.loadCachedUser()
    }

    override fun saveChanges() {
        editedUserDataSource.saveChanges()
    }
}
