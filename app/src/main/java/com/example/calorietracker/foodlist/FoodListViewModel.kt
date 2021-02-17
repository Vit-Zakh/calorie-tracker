package com.example.calorietracker.foodlist

import androidx.lifecycle.*
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.repositories.FoodListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val foodListRepository: FoodListRepository
) : ViewModel() {

    private val _foodListData = MutableLiveData<List<RecyclerData.Food>>()
    private val _currentUserData = MutableLiveData<RecyclerData.User>()
//        .also { it.value = RecyclerData.User() }

    init {
        viewModelScope.launch {
            foodListRepository.fetchUser().collect { _currentUserData.value = it }
        }
        viewModelScope.launch { foodListRepository.refreshUser() }
        viewModelScope.launch {
            foodListRepository.fetchFood().collect { _foodListData.value = it }
        }
        viewModelScope.launch { foodListRepository.refreshFood() }
    }

    val currentUserData = _currentUserData
    val foodListData = _foodListData

    fun addMealToList(meal: RecyclerData.Meal) {
        viewModelScope.launch {
        }
    }

    fun addFood(food: RecyclerData.Food) {
        viewModelScope.launch {
            foodListRepository.addFood(food)
        }
    }

    fun deleteFood(index: Int) {
        viewModelScope.launch {
            foodListRepository.deleteFood(index)
        }
    }
}
