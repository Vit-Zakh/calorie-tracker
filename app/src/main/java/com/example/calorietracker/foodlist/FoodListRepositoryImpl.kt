package com.example.calorietracker.foodlist

import android.util.Log
import com.example.calorietracker.models.network.FoodResponse
import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.network.NetworkResponse
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.state.FoodListState
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserState
import com.example.calorietracker.utils.ListStates
import com.example.calorietracker.utils.Operations
import kotlinx.coroutines.flow.StateFlow

class FoodListRepositoryImpl(
    private val userState: UserState,
    private val foodListState: FoodListState,
    private val mealsState: MealsState,
    private val apiService: TrackerApiService
) : FoodListRepository {

    override val user: StateFlow<UserResponse> = userState.cashedUser
    override val food: StateFlow<List<FoodResponse>> = foodListState.cashedFoodList

    override val listState: StateFlow<ListStates> = foodListState.listState

    override fun addFood(food: FoodProps) {
        foodListState.addFood(food)
    }

    override fun deleteFood(index: Int) {
        foodListState.deleteFood(index)
    }

    override fun addMealToList(mealProps: DailyIntakeProps.MealProps) {
        userState.changeProgress(mealProps, Operations.ADDITION)
        mealsState.addMeal(mealProps)
    }

    override suspend fun refreshFood() {
//        when (val fetchedFood = apiService.getFoodList()) {
//            is NetworkResponse.Success -> {
//                foodListState.refreshFoodList(fetchedFood.body)
//                Log.d("TAG", "refreshState: SUCCESS")
//            }
//            is NetworkResponse.ApiError -> throw RuntimeException(fetchedFood.body)
//            is NetworkResponse.NetworkError -> throw RuntimeException(fetchedFood.error)
//            is NetworkResponse.UnknownError -> throw RuntimeException(fetchedFood.error)
//        }

        foodListState.refreshFoodList(apiService.getFoodList())
    }

    override suspend fun refreshUser() {
        when (val fetchedUser = apiService.getUser()) {
            is NetworkResponse.Success -> {
                userState.refreshUser(fetchedUser.body)
                Log.d("TAG", "refreshState: SUCCESS")
            }
//            is NetworkResponse.ApiError -> throw RuntimeException(fetchedUser.code.toString())
//            is NetworkResponse.NetworkError -> throw RuntimeException(fetchedUser.error)
//            is NetworkResponse.UnknownError -> throw RuntimeException(fetchedUser.error)
        }
    }
}