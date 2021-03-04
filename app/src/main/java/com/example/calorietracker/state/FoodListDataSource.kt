package com.example.calorietracker.state

import com.example.calorietracker.models.network.FoodListResponse
import com.example.calorietracker.models.network.FoodResponse
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.models.ui.mapToDomainModel
import com.example.calorietracker.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Error
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListDataSource @Inject constructor() {

    data class FoodState(
        val foodList: List<FoodResponse> = listOf(),
        val isLoading: Boolean = false,
        val isFailed: Boolean = false
    )

    private val _cashedFoodList = MutableStateFlow(FoodState())

    val cashedFoodList: StateFlow<FoodState> = _cashedFoodList

    fun startFetching() {
        _cashedFoodList.value = _cashedFoodList.value.copy(isLoading = true)
    }

    fun refreshFoodList(food: NetworkResponse<FoodListResponse, Error>) {
        when (food) {
            is NetworkResponse.Success -> _cashedFoodList.value = _cashedFoodList.value.copy(foodList = food.body.food, isLoading = false)
            else -> {
                _cashedFoodList.value = _cashedFoodList.value.copy(isLoading = false, isFailed = true)
            }
        }
    }

    fun addFood(food: FoodProps) {
        val updatedList = _cashedFoodList.value.foodList.toMutableList()
        updatedList.add(0, food.mapToDomainModel())
        _cashedFoodList.value = _cashedFoodList.value.copy(foodList = updatedList, isLoading = false, isFailed = false)
    }

    fun deleteFood(index: Int) {
        val updatedList = _cashedFoodList.value.foodList.toMutableList()
        updatedList.removeAt(index)
        _cashedFoodList.value = _cashedFoodList.value.copy(foodList = updatedList, isLoading = false, isFailed = false)
    }
}
