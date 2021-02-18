package com.example.calorietracker.dailyintake

import androidx.lifecycle.*
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.UserResponse
import com.example.calorietracker.repositories.DailyIntakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

    private val _dailyLiveData: MediatorLiveData<List<RecyclerData>> = MediatorLiveData()

    val dailyLiveData: MutableLiveData<List<RecyclerData>> = MutableLiveData(listOf())

    init {

        val userLiveData: LiveData<UserResponse> = dailyIntakeRepository.user.asLiveData()

        viewModelScope.launch {
            dailyIntakeRepository.refreshState()
         val mealsLiveData = dailyIntakeRepository.meals.collect {
                val some = it
            }
        }
//
//        _dailyLiveData.addSource(userLiveData) {
//            val user = userLiveData.value ?: RecyclerData.User()
//            val mealsList = mealsLiveData.value ?: listOf()
//            _dailyLiveData.value = listOf(user, RecyclerData.TextLine) + mealsList
//        }
//        _dailyLiveData.addSource(mealsLiveData) {
//            val user = userLiveData.value ?: RecyclerData.User()
//            val mealsList = mealsLiveData.value ?: listOf()
//            _dailyLiveData.value = listOf(user, RecyclerData.TextLine) + mealsList
//        }
    }

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
