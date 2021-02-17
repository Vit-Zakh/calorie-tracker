package com.example.calorietracker.foodlist

import android.util.Log
import androidx.lifecycle.*
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.repositories.FoodListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val foodListRepository: FoodListRepository
) : ViewModel() {

    private lateinit var _foodListData: LiveData<List<RecyclerData.Food>>
    private lateinit var _currentUserData: LiveData<RecyclerData.User>
    var testDataValue = MutableLiveData<List<RecyclerData.Food>>()

    init {
        viewModelScope.launch {
            _currentUserData = foodListRepository.fetchUser().asLiveData()
        }
        viewModelScope.launch {
            _foodListData = foodListRepository.fetchFood().asLiveData()
        }
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
            Log.d("_VIEWMODEL", "addFood: called")
            _foodListData = foodListRepository.fetchFood().asLiveData()
        }
    }

    fun deleteFood(id: Int) {
        viewModelScope.launch {
        }
    }
}
