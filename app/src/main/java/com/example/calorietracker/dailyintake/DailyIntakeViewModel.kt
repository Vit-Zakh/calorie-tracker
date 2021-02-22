package com.example.calorietracker.dailyintake

import androidx.lifecycle.*
import com.example.calorietracker.models.network.MealResponse
import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            dailyIntakeRepository.refreshState()
        }
    }

    val dailyLiveData: LiveData<List<DailyIntakeProps>> =
        dailyIntakeRepository.user.combine(dailyIntakeRepository.meals) { user: UserResponse, list: List<MealResponse> ->
            listOf(user.mapToUiModel(), DailyIntakeProps.TextLine) + list.map {
                it.mapToUiModel()
            }
        }.asLiveData()

    fun addMeal(mealProps: DailyIntakeProps.MealProps) {
        dailyIntakeRepository.addMeal(mealProps)
    }

    fun removeMeal(index: Int) {
        dailyIntakeRepository.deleteMeal(index)
    }
}
