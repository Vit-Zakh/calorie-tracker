package com.example.calorietracker.state

import android.content.SharedPreferences
import com.example.calorietracker.models.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditedUserDataSource @Inject constructor(private val sharedPreferences: SharedPreferences, private val userDataSource: UserDataSource) {

    data class EditedUserState(
        val userData: UserResponse = UserResponse(),
        val isChanging: Boolean = false,
        val canBeSaved: Boolean = false,
    )

    private val _editedUserFlow = MutableStateFlow(EditedUserState())

    val editedUserFlow: StateFlow<EditedUserState> = _editedUserFlow

    val editedUser = _editedUserFlow.value.userData

    fun setChangingState() {
        _editedUserFlow.value = _editedUserFlow.value.copy(isChanging = true)
    }

//    fun loadCachedUser() {
//        _editedUserFlow.value = EditedUserState(
//            UserResponse(
//                id = "cachedUser",
//                name = sharedPreferences.getString("USER_NAME", ""),
//                weight = sharedPreferences.getString("USER_WEIGHT", "")?.toFloatOrNull(),
//                maxIntake = sharedPreferences.getString("USER_INCOME", "")?.toFloatOrNull(),
//                image = sharedPreferences.getString("USER_IMAGE_URL", ""),
//                backgroundImage = sharedPreferences.getString("USER_BACKGROUND_URL", ""),
//                age = sharedPreferences.getString("USER_AGE", null)?.toIntOrNull(),
//            ),
//            isChanging = false,
//        )
//    }

    fun loadUserToEdit(userData: UserResponse) {
        _editedUserFlow.value = EditedUserState(userData, isChanging = false)
    }

    fun changeProfilePreview(uri: String) {
        val userWithNewProfileImage = _editedUserFlow.value.userData
        userWithNewProfileImage.image = uri
        _editedUserFlow.value = _editedUserFlow.value.copy(
            userData = userWithNewProfileImage,
            isChanging = false,
            canBeSaved = true
        )
    }

    fun changeBackgroundPreview(uri: String) {
        val userWithNewBackgroundImage = _editedUserFlow.value.userData
        userWithNewBackgroundImage.backgroundImage = uri
        _editedUserFlow.value = _editedUserFlow.value.copy(
            userData = userWithNewBackgroundImage,
            isChanging = false,
            canBeSaved = true
        )
    }
}
