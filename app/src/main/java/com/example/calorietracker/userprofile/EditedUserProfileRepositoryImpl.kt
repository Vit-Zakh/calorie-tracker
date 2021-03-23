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
    override val editedUser: StateFlow<EditedUserDataSource.EditedUserState> =
        editedUserDataSource.editedUserFlow

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
    }

    override fun changeProfilePreview(uri: String) {
        editedUserDataSource.changeProfilePreview(uri)
    }

    override fun changeBackgroundPreview(uri: String) {
        editedUserDataSource.changeBackgroundPreview(uri)
    }

    override fun loadCachedUser() {
        editedUserDataSource.loadUserToEdit(userDataSource.fetchUserInfo())
    }

    override fun saveChanges() {
        userDataSource.setLoadingState()
        userDataSource.saveChanges(editedUserDataSource.editedUser)
    }

    override fun changeUserWeight(weight: Float) {
        userDataSource.setLoadingState()
        editedUserDataSource.changeUserWeight(weight)
    }

    override fun changeUserName(name: String) {
        userDataSource.setLoadingState()
        editedUserDataSource.changeUserName(name)
    }

    override fun changeUserAge(age: Int?) {
        userDataSource.setLoadingState()
        editedUserDataSource.changeUserAge(age)
    }

    override fun changeUserIntake(intake: Float) {
        userDataSource.setLoadingState()
        editedUserDataSource.changeUserIntake(intake)
    }
}
