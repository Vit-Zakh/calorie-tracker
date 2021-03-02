package com.example.calorietracker.foodlist

import androidx.lifecycle.*
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodListProps
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
//            foodListRepository.refreshUser()
        }
    }

    val currentUserData: LiveData<DailyIntakeProps> = foodListRepository.user.map { userState ->
        when {
            userState.isLoading -> DailyIntakeProps.LoadingUser
            userState.isFailed -> DailyIntakeProps.FailedUser
            (userState.fetchedUSer.id != "-1") -> DailyIntakeProps.LoadedUser(userState.fetchedUSer.mapToUiModel())
            else -> DailyIntakeProps.LoadingUser
        }
    }.asLiveData()

    val foodListData: LiveData<FoodListProps> =
        foodListRepository.food.map { listState ->
            when {
                listState.isLoading -> FoodListProps.LoadingFoodList
                listState.isFailed -> FoodListProps.FailedFoodList
                listState.foodList.isEmpty() -> FoodListProps.EmptyFoodList
                listState.foodList.isNotEmpty() -> FoodListProps.LoadedFoodList(listState.foodList.map { it.mapToUiModel() })
                else -> FoodListProps.LoadingFoodList
            }
        }.asLiveData()

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
