package com.example.calorietracker.dailyintake

import androidx.lifecycle.*
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.MealsListResponse
import com.example.calorietracker.network.UserResponse
import com.example.calorietracker.network.mapToUiModel
import com.example.calorietracker.repositories.DailyIntakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

    lateinit var _dailyLiveData: LiveData<List<RecyclerData>>

    val userFlow: MutableStateFlow<UserResponse> = dailyIntakeRepository.user
    val mealsFlow: MutableStateFlow<MealsListResponse> = dailyIntakeRepository.meals

    init {
        viewModelScope.launch {
            val combinedFlow: Flow<List<RecyclerData>> = userFlow.combine(mealsFlow) { user: UserResponse, list: MealsListResponse ->
                listOf<RecyclerData>(user.mapToUiModel(), RecyclerData.TextLine) + list.mapToUiModel()
            }
            _dailyLiveData = combinedFlow.asLiveData()
            dailyIntakeRepository.refreshState()
        }
    }

    var dailyLiveData: LiveData<List<RecyclerData>> = _dailyLiveData

    fun addMeal(meal: RecyclerData.Meal) {
        viewModelScope.launch {
            dailyIntakeRepository.addMeal(meal)
        }
    }

    fun removeMeal(index: Int) {
        viewModelScope.launch {
            dailyIntakeRepository.removeMeal(index)
        }
    }
}
