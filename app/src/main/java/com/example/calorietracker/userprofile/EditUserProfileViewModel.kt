package com.example.calorietracker.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.calorietracker.dailyintake.DailyIntakeRepository
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class EditUserProfileViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

    val currentUserData: LiveData<DailyIntakeProps> = dailyIntakeRepository.user.map { userState ->
        when {
            userState.isLoading -> DailyIntakeProps.LoadingUser
            userState.isFailed -> DailyIntakeProps.FailedUser
            userState.fetchedUSer.id.isNotBlank() -> DailyIntakeProps.LoadedUser(userState.fetchedUSer.mapToUiModel())
            else -> DailyIntakeProps.LoadingUser
        }
    }.asLiveData()

}
