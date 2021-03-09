package com.example.calorietracker.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    val currentUserData: LiveData<DailyIntakeProps> = userProfileRepository.user.map { userState ->
        when {
            userState.isLoading -> DailyIntakeProps.LoadingUser
            userState.isFailed -> DailyIntakeProps.FailedUser
            userState.fetchedUSer.id.isNotBlank() -> DailyIntakeProps.LoadedUser(userState.fetchedUSer.mapToUiModel())
            else -> DailyIntakeProps.LoadingUser
        }
    }.asLiveData()

    fun saveToSharedPreferences(userName: String, userWeight: String, userAge: String, userIncome: String, userImageUrl: String, userBackgroundUrl: String) {
        userProfileRepository.saveToSharedPreferences(userName, userWeight, userAge, userIncome, userImageUrl, userBackgroundUrl)
    }
}
