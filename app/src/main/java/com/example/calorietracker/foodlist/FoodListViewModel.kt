package com.example.calorietracker.foodlist

import androidx.lifecycle.*
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.FoodListResponse
import com.example.calorietracker.network.UserResponse
import com.example.calorietracker.network.mapToUiModel
import com.example.calorietracker.repositories.FoodListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val foodListRepository: FoodListRepository
) : ViewModel() {

    private lateinit var _foodListData: LiveData<List<RecyclerData.Food>>
    private lateinit var _currentUserData: LiveData<RecyclerData.User>

    private val userFlow: MutableStateFlow<UserResponse> = foodListRepository.user
    private val foodListFlow: MutableStateFlow<FoodListResponse> = foodListRepository.food

    init {
        viewModelScope.launch {
            _foodListData = foodListFlow.map { it.mapToUiModel() }.asLiveData()
            _currentUserData = userFlow.map { it.mapToUiModel() }.asLiveData()
            foodListRepository.refreshFood()
            foodListRepository.refreshUser()
        }
    }

    val currentUserData = _currentUserData
    val foodListData = _foodListData

    fun addMealToList(meal: RecyclerData.Meal) {
//        viewModelScope.launch {
//            foodListRepository.addMealToList(meal)
//        }
    }

    fun addFood(food: RecyclerData.Food) {
//        viewModelScope.launch {
//            foodListRepository.addFood(food)
//        }
    }

    fun deleteFood(index: Int) {
//        viewModelScope.launch {
//            foodListRepository.deleteFood(index)
//        }
    }
}
