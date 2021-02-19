package com.example.calorietracker.dailyintake

import androidx.lifecycle.*
import com.example.calorietracker.extensions.mapToUiModel
import com.example.calorietracker.models.DomainModel
import com.example.calorietracker.models.UiModel
import com.example.calorietracker.network.UserResponse
import com.example.calorietracker.network.mapToUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

    private val userFlow: StateFlow<UserResponse> = dailyIntakeRepository.user
    private val mealsFlow: StateFlow<List<DomainModel.Meal>> = dailyIntakeRepository.meals

    init {
        viewModelScope.launch {
            dailyIntakeRepository.refreshState()
        }
    }

    val dailyLiveData: LiveData<List<UiModel>> =
        userFlow.combine(mealsFlow) { user: UserResponse, list: List<DomainModel.Meal> ->
            listOf(user.mapToUiModel(), UiModel.TextLine) + list.map {
                it.mapToUiModel()
            }
        }.asLiveData()

    fun addMeal(meal: UiModel.Meal) {
        dailyIntakeRepository.addMeal(meal)
    }

    fun removeMeal(index: Int) {
        dailyIntakeRepository.deleteMeal(index)
    }
}
