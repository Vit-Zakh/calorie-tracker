package com.example.calorietracker.state

import com.example.calorietracker.models.network.FoodListResponse
import com.example.calorietracker.models.network.FoodResponse
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.models.ui.mapToDomainModel
import com.example.calorietracker.network.NetworkResponse
import com.example.calorietracker.utils.ListStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Error
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListState @Inject constructor() {
    private val _cashedFoodList = MutableStateFlow(listOf<FoodResponse>())

    val listState = MutableStateFlow<ListStates>(ListStates.LOADING)

    val cashedFoodList: StateFlow<List<FoodResponse>> = _cashedFoodList

    fun refreshFoodList(food: NetworkResponse<FoodListResponse, Error>) {

        listState.value = ListStates.LOADING

        when (food) {
            is NetworkResponse.Success -> {
                if (food.body.food.isNotEmpty()) {
                    listState.value = ListStates.SUCCESS
                    _cashedFoodList.value = food.body.food
                } else listState.value = ListStates.SUCCESS_EMPTY_LIST
            }
            else -> {
                listState.value = ListStates.ERROR
            }
        }
    }

    fun addFood(food: FoodProps) {
        val updatedList = _cashedFoodList.value.toMutableList()
        updatedList.add(0, food.mapToDomainModel())
        _cashedFoodList.value = updatedList
    }

    fun deleteFood(index: Int) {
        val updatedList = _cashedFoodList.value.toMutableList()
        updatedList.removeAt(index)
        _cashedFoodList.value = updatedList
    }
}
