package com.example.calorietracker.userprofile

import com.example.calorietracker.state.EditedUserDataSource
import kotlinx.coroutines.flow.StateFlow

interface EditedUserProfileRepository {

    val editedUser: StateFlow<EditedUserDataSource.EditedUserState>

    fun saveToSharedPreferences(userName: String, userWeight: String, userAge: String, userIncome: String, userImageUrl: String, userBackgroundUrl: String)

    fun changeProfilePreview(uri: String)

    fun changeBackgroundPreview(uri: String)

    fun loadCachedUser()

    fun saveChanges()

}
