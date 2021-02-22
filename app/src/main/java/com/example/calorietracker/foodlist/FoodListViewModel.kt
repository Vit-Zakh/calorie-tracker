package com.example.calorietracker.foodlist

import androidx.lifecycle.*
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodProps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val foodListRepository: FoodListRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            foodListRepository.refreshFood()
            foodListRepository.refreshUser()
        }
    }

    val currentUserData = foodListRepository.user.map { it.mapToUiModel() }.asLiveData()
    val foodListData: LiveData<List<FoodProps>> =
        foodListRepository.food.map { it.map { food -> food.mapToUiModel() } }.asLiveData()

    fun addMealToList(mealProps: DailyIntakeProps.MealProps) {
        foodListRepository.addMealToList(mealProps)
    }

    fun addFood(food: FoodProps) {
        foodListRepository.addFood(food)
    }

    fun deleteFood(index: Int) {
        foodListRepository.deleteFood(index)
    }
}
