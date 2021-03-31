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

        sharedPreferences.edit()
            .putString("USER_NAME", editedUserDataSource.editedUser.name)
            .putString("USER_WEIGHT", editedUserDataSource.editedUser.weight.toString())
            .putString("USER_AGE", editedUserDataSource.editedUser.age.toString())
            .putString("USER_INCOME", editedUserDataSource.editedUser.maxIntake.toString())
            .putString("USER_IMAGE_URL", editedUserDataSource.editedUser.image)
            .putString("USER_BACKGROUND_URL", editedUserDataSource.editedUser.backgroundImage)
            .apply()
    }

    override fun changeUserWeight(weight: Float?) {
        editedUserDataSource.changeUserWeight(weight)
    }

    override fun changeUserName(name: String) {
        editedUserDataSource.changeUserName(name)
    }

    override fun changeUserAge(age: Int?) {
        editedUserDataSource.changeUserAge(age)
    }

    override fun changeUserIntake(intake: Float?) {
        editedUserDataSource.changeUserIntake(intake)
    }
}
