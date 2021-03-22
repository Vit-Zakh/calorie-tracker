package com.example.calorietracker.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class EditUserProfileViewModel @Inject constructor(
    private val editedUserProfileRepository: EditedUserProfileRepository
) : ViewModel() {

    init {
        editedUserProfileRepository.loadCachedUser()
    }

    val canBeSaved: MutableLiveData<Boolean> = MutableLiveData(false)

    val currentUserData: LiveData<DailyIntakeProps> =
        editedUserProfileRepository.editedUser.map { userState ->
            canBeSaved.value = userState.canBeSaved
            DailyIntakeProps.LoadedUser(userState.userData.mapToUiModel())
        }.asLiveData()

    fun changeUserName(name: String) {
        editedUserProfileRepository.changeUserName(name)
    }

    fun changeUserWeight(weight: Float) {
        editedUserProfileRepository.changeUserWeight(weight)
    }

    fun changeUserAge(age: Int?) {
        editedUserProfileRepository.changeUserAge(age)
    }

    fun changeUserIntake(intake: Float) {
        editedUserProfileRepository.changeUserIntake(intake)
    }

    fun saveToSharedPreferences(
        userName: String,
        userWeight: String,
        userAge: String,
        userIncome: String,
        userImageUrl: String,
        userBackgroundUrl: String
    ) {
        editedUserProfileRepository.saveToSharedPreferences(
            userName,
            userWeight,
            userAge,
            userIncome,
            userImageUrl,
            userBackgroundUrl
        )
    }

    fun changeProfilePreview(uri: String) {
        editedUserProfileRepository.changeProfilePreview(uri)
    }

    fun changeBackgroundPreview(uri: String) {
        editedUserProfileRepository.changeBackgroundPreview(uri)
    }

    fun saveChanges() {
        editedUserProfileRepository.saveChanges()
    }
}
