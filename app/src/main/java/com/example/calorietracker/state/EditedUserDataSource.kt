package com.example.calorietracker.state

import com.example.calorietracker.models.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditedUserDataSource @Inject constructor() {

    data class EditedUserState(
        val userData: UserResponse = UserResponse(),
        val isChanging: Boolean = false,
        val canBeSaved: Boolean = false,
    )

    private val _editedUserFlow = MutableStateFlow(EditedUserState())

    val editedUserFlow: StateFlow<EditedUserState> = _editedUserFlow

    val editedUser: UserResponse
        get() {
            return _editedUserFlow.value.userData
        }

    fun setChangingState() {
        _editedUserFlow.value = _editedUserFlow.value.copy(isChanging = true)
    }

    fun loadUserToEdit(userData: UserResponse) {
        _editedUserFlow.value = EditedUserState(userData, isChanging = false)
    }

    fun changeUserName(editedName: String) {
        val userWithNewName = _editedUserFlow.value.userData
        userWithNewName.name = editedName
        _editedUserFlow.value = _editedUserFlow.value.copy(
            userData = userWithNewName,
            isChanging = false,
            canBeSaved = true
        )
    }

    fun changeUserWeight(editedWeight: Float) {
        val userWithNewWeight = _editedUserFlow.value.userData
        userWithNewWeight.weight = editedWeight
        _editedUserFlow.value = _editedUserFlow.value.copy(
            userData = userWithNewWeight,
            isChanging = false,
            canBeSaved = true
        )
    }

    fun changeUserAge(editedAge: Int?) {
        val userWithNewAge = _editedUserFlow.value.userData
        userWithNewAge.age = editedAge
        _editedUserFlow.value = _editedUserFlow.value.copy(
            userData = userWithNewAge,
            isChanging = false,
            canBeSaved = true
        )
    }

    fun changeUserIntake(editedIntake: Float) {
        val userWithNewIntake = _editedUserFlow.value.userData
        userWithNewIntake.maxIntake = editedIntake
        _editedUserFlow.value = _editedUserFlow.value.copy(
            userData = userWithNewIntake,
            isChanging = false,
            canBeSaved = true
        )
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
