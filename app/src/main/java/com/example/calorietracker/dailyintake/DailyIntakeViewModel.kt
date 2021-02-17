package com.example.calorietracker.dailyintake

import androidx.lifecycle.*
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.repositories.DailyIntakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

    private var _dailyIntakeData = MutableLiveData<List<RecyclerData>>()

    init {
        viewModelScope.launch {
            dailyIntakeRepository.fetchData().collect { _dailyIntakeData.value = it }
        }
        viewModelScope.launch { dailyIntakeRepository.refreshState() }
    }

    var dailyIntakeData: LiveData<List<RecyclerData>> = _dailyIntakeData

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
