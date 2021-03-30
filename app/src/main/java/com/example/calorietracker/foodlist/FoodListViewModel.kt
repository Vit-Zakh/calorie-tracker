package com.example.calorietracker.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodListProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.models.ui.mapToDomainModel
import com.example.calorietracker.redux.actions.AddFood
import com.example.calorietracker.redux.actions.RemoveFood
import com.example.calorietracker.redux.states.AppState
import com.example.calorietracker.redux.store.AppStore
import com.example.calorietracker.redux.store.StateChangeListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val store: AppStore
) : ViewModel(), StateChangeListener {

    init {

        store.subscribe(this)
    }

    class FoodListFragmentProps(
        val userData: DailyIntakeProps,
        val foodData: FoodListProps,
        val addAction: (FoodProps) -> Unit,
        val removeAction: () -> Unit
    )

    private val _foodListFragmentProps: MutableLiveData<FoodListFragmentProps> = MutableLiveData(
        FoodListFragmentProps(
            userData = DailyIntakeProps.LoadingUser,
            foodData = FoodListProps.LoadingFoodList,
            addAction = ::addFood,
            removeAction = ::removeFood
        )
    )

    val foodListFragmentProps: LiveData<FoodListFragmentProps> = _foodListFragmentProps

    override fun onCleared() {
        store.unsubscribe(this)
    }

    fun addMealToList(mealProps: DailyIntakeProps.MealProps) {
        TODO()
    }

    fun addFood(food: FoodProps) {
        store.dispatch(AddFood(food.mapToDomainModel()))
    }

    fun removeFood() {
        store.dispatch(RemoveFood(3))
    }

    override fun onUpdate(state: AppState) {
        val _foodList = when {
            state.foodsState.isLoading -> FoodListProps.LoadingFoodList
            state.foodsState.isFailed -> FoodListProps.FailedFoodList
            state.foodsState.foodsList.isEmpty() -> FoodListProps.EmptyFoodList
            state.foodsState.foodsList.isNotEmpty() -> FoodListProps.LoadedFoodList(state.foodsState.foodsList.map { it.mapToUiModel() })
            else -> FoodListProps.LoadingFoodList
        }

        val _user = when {
            state.userState.isLoading ->
                DailyIntakeProps.LoadingUser
            state.userState.isFailed ->
                DailyIntakeProps.FailedUser
            state.userState.userData.id.isNotBlank() ->
                DailyIntakeProps.LoadedUser(state.userState.userData.mapToUiModel()).user
            else ->
                DailyIntakeProps.FailedUser
        }
        _foodListFragmentProps.postValue(FoodListFragmentProps(userData = _user, foodData = _foodList, removeAction = ::removeFood, addAction = ::addFood))
    }

    /** Test functions block */

//    fun showEmptyList() {
//        foodListRepository.showEmptyList()
//    }
//
//    fun showFailedList() {
//        foodListRepository.showFailedList()
//    }

    /** End of test functions block */
}
