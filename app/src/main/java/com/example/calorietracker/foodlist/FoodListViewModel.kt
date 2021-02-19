package com.example.calorietracker.foodlist

import androidx.lifecycle.*
import com.example.calorietracker.extensions.mapToUiModel
import com.example.calorietracker.models.DomainModel
import com.example.calorietracker.models.UiModel
import com.example.calorietracker.network.UserResponse
import com.example.calorietracker.network.mapToUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val foodListRepository: FoodListRepository
) : ViewModel() {

    private val userFlow: StateFlow<UserResponse> = foodListRepository.user
    private val foodListFlow: StateFlow<List<DomainModel.Food>> = foodListRepository.food

    init {
        viewModelScope.launch {
            foodListRepository.refreshFood()
            foodListRepository.refreshUser()
        }
    }

    val currentUserData = userFlow.map { it.mapToUiModel() }.asLiveData()
    val foodListData = foodListFlow.map {
        it.map { food ->
            food.mapToUiModel()
        }
    }.asLiveData()

    fun addMealToList(meal: UiModel.Meal) {
        foodListRepository.addMealToList(meal)
    }

    fun addFood(food: UiModel.Food) {
        foodListRepository.addFood(food)
    }

    fun deleteFood(index: Int) {
        foodListRepository.deleteFood(index)
    }
}
