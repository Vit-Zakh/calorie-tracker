package com.example.calorietracker.foodlist

import androidx.lifecycle.*
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.network.mapToUiModel_1
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodListProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.utils.ListStates
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
//    val foodListData: LiveData<List<FoodProps>> =
//        foodListRepository.food.map { it.map { food -> food.mapToUiModel() } }.asLiveData()
    val foodListData_1: LiveData<List<FoodListProps.FoodProps>> =
        foodListRepository.food.map { it.map { food -> food.mapToUiModel_1() } }.asLiveData()

    val listState: LiveData<ListStates> = foodListRepository.listState.asLiveData()

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
