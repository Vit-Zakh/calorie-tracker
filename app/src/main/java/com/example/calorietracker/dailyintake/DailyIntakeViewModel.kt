package com.example.calorietracker.dailyintake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.persistence.TrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dataSource: DataSource
) : ViewModel() {

    private val repository = TrackerRepository(dataSource)

    private var _dailyIntakeData: MutableLiveData<List<RecyclerData>> =
        MutableLiveData<List<RecyclerData>>().apply {
            this.value = repository.intakeDataList
        }

    val dailyIntakeData: LiveData<List<RecyclerData>>
        get() {
            return _dailyIntakeData
        }

    init {
        viewModelScope.launch {
            repository.fetchInitialData()
            _dailyIntakeData.value = repository.intakeDataList
        }

        viewModelScope.launch {
            delay(1000)
            repository.fetchMeals()
            _dailyIntakeData.value = repository.intakeDataList
        }

        viewModelScope.launch {
            delay(2000)
            repository.fetchUserData()
            _dailyIntakeData.value = repository.intakeDataList
        }
    }

    suspend fun addMeal(meal: RecyclerData.Meal) {
        viewModelScope.launch {
            repository.addMeal(meal)
            _dailyIntakeData.value = repository.intakeDataList
        }
    }

    suspend fun removeMeal(id: Int) {
        viewModelScope.launch {
            repository.removeMeal(id)
            _dailyIntakeData.value = repository.intakeDataList
        }
    }
}
