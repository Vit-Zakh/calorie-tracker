package com.example.calorietracker.userprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.dailyintake.DailyIntakeRepository
import com.example.calorietracker.models.ui.DailyIntakeProps.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

//    val currentUserData: LiveData<DailyIntakeProps> = dailyIntakeRepository.user.map { userState ->
//        when {
//            userState.isLoading -> LoadingUser
//            userState.isFailed -> FailedUser
//            userState.fetchedUSer.id.isNotBlank() -> LoadedUser(userState.fetchedUSer.mapToUiModel())
//            else -> LoadingUser
//        }
//    }.asLiveData()

    val currentUserData = MutableLiveData(UserProps())
}
