package com.example.calorietracker.dailyintake

import androidx.lifecycle.*
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.repositories.DailyIntakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

    private lateinit var _dailyIntakeData: LiveData<List<RecyclerData>>

    init {
        viewModelScope.launch {
            _dailyIntakeData = dailyIntakeRepository.fetchData().asLiveData()
        }
    }

    var dailyIntakeData: LiveData<List<RecyclerData>> = _dailyIntakeData

    fun addMeal(meal: RecyclerData.Meal) {
        viewModelScope.launch {
            dailyIntakeRepository.addMeal(meal)
        }
    }

    fun removeMeal(id: Int) {
        viewModelScope.launch {
        }
    }
}
