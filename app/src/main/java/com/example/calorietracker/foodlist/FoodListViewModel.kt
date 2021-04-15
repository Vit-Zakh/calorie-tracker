package com.example.calorietracker.foodlist

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodListProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.models.ui.mapToDomainModel
import com.example.calorietracker.redux.actions.*
import com.example.calorietracker.redux.states.AppState
import com.example.calorietracker.redux.store.AppStore
import com.example.calorietracker.redux.store.StateChangeListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val store: AppStore
) : ViewModel(), StateChangeListener {

    private val _foodListFragmentProps: MutableLiveData<FoodListFragmentProps> = MutableLiveData(
        FoodListFragmentProps(
            userData = DailyIntakeProps.LoadingUser,
            foodData = FoodListProps.LoadingFoodList,
            foodInDialog = FoodProps("", "", "", 0f),
            addAction = ::addFood,
            removeAction = ::removeFood,

            openDialogAction = ::openDialog,
            dismissDialogAction = ::dismissDialog,
            addMealDialogAction = ::addMealToList
        )
    )

    init {
        store.subscribe(this)
    }

    class FoodListFragmentProps(
        val userData: DailyIntakeProps,
        val foodData: FoodListProps,
        val foodInDialog: FoodProps,
        val addAction: (FoodProps) -> Unit,
        val removeAction: () -> Unit,

        val openDialogAction: (food: FoodProps) -> Unit,
        val dismissDialogAction: (dialog: DialogFragment) -> Unit,
        val addMealDialogAction: (meal: DailyIntakeProps.MealProps, dialog: DialogFragment) -> Unit
    )

    val foodListFragmentProps: LiveData<FoodListFragmentProps> = _foodListFragmentProps

    override fun onCleared() {
        store.unsubscribe(this)
    }

    fun addMealToList(mealProps: DailyIntakeProps.MealProps, dialog: DialogFragment) {
        store.dispatch(AddMeal(meal = mealProps.mapToDomainModel()))
        dismissDialog(dialog)
    }

    fun addFood(food: FoodProps) {
        store.dispatch(AddFood(food.mapToDomainModel()))
    }

    fun removeFood() {
        store.dispatch(RemoveFood(3))
    }

    fun openDialog(food: FoodProps) {
        store.dispatch(OpenFoodDialog(food))
    }

    fun dismissDialog(dialog: DialogFragment?) {
        dialog?.let { store.dispatch(CloseDialogAction(it)) }
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

        val _foodInDialog = state.foodDialogState.food

        _foodListFragmentProps.postValue(
            FoodListFragmentProps(
                userData = _user,
                foodData = _foodList,
                foodInDialog = _foodInDialog,
                removeAction = ::removeFood,
                addAction = ::addFood,
                openDialogAction = ::openDialog,
                dismissDialogAction = ::dismissDialog,
                addMealDialogAction = ::addMealToList
            )
        )
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
