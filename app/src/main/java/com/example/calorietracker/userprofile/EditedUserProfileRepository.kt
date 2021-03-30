package com.example.calorietracker.userprofile

import com.example.calorietracker.state.EditedUserDataSource
import kotlinx.coroutines.flow.StateFlow

interface EditedUserProfileRepository {

    val editedUser: StateFlow<EditedUserDataSource.EditedUserState>

    fun changeProfilePreview(uri: String)

    fun changeBackgroundPreview(uri: String)

    fun loadCachedUser()

    fun saveChanges()

    fun changeUserWeight(weight: Float?)

    fun changeUserName(name: String)

    fun changeUserAge(age: Int?)

    fun changeUserIntake(intake: Float?)
}
