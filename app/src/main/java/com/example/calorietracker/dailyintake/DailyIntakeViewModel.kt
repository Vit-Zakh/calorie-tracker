package com.example.calorietracker.dailyintake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.mapToDomainModel
import com.example.calorietracker.modo.Screens
import com.example.calorietracker.redux.actions.AddMeal
import com.example.calorietracker.redux.actions.ChangeScreen
import com.example.calorietracker.redux.actions.RemoveMeal
import com.example.calorietracker.redux.states.AppState
import com.example.calorietracker.redux.store.AppStore
import com.example.calorietracker.redux.store.StateChangeListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val store: AppStore
) : ViewModel(), StateChangeListener {

    private val _dailyFragmentProps: MutableLiveData<DailyFragmentProps> = MutableLiveData(
        DailyFragmentProps(
            list = mutableListOf(
                DailyIntakeProps.LoadingUser,
                DailyIntakeProps.TextLine,
                DailyIntakeProps.LoadingMealsItem
            ),
            addAction = ::addMeal,
            removeAction = ::removeMeal,
            navigationActionFoodList = ::moveToFoodList,
            navigationActionCharactersList = ::moveToCharactersList
        )
    )

    init {
        store.subscribe(this)
    }

    class DailyFragmentProps(
        val list: List<DailyIntakeProps>,
        val addAction: (DailyIntakeProps.MealProps) -> Unit,
        val removeAction: () -> Unit,
        val navigationActionFoodList: () -> Unit,
        val navigationActionCharactersList: () -> Unit
    )

    val dailyFragmentProps: LiveData<DailyFragmentProps> = _dailyFragmentProps
    override fun onCleared() {
        store.unsubscribe(this)
    }

    private fun addMeal(mealProps: DailyIntakeProps.MealProps) {
        store.dispatch(AddMeal(meal = mealProps.mapToDomainModel()))
    }

    private fun removeMeal() {
        store.dispatch(RemoveMeal(2))
    }

    private fun moveToFoodList() {
        store.dispatch(ChangeScreen(Screens.FoodListScreen()))
    }

    private fun moveToCharactersList() {
        store.dispatch(ChangeScreen(Screens.CharactersListScreen()))
    }

    override fun onUpdate(state: AppState) {
        val _dailyIntakeData = mutableListOf<DailyIntakeProps>()
        val user =
            when {
                state.userState.isLoading ->
                    DailyIntakeProps.LoadingUser
                state.userState.isFailed ->
                    DailyIntakeProps.FailedUser
                state.userState.userData.id.isNotBlank() ->
                    DailyIntakeProps.LoadedUser(state.userState.userData.mapToUiModel()).user
                else ->
                    DailyIntakeProps.FailedUser
            }
        _dailyIntakeData.add(user)
        _dailyIntakeData.add(DailyIntakeProps.TextLine)
        when {
            state.mealsListState.isLoading ->
                _dailyIntakeData.add(DailyIntakeProps.LoadingMealsItem)
            state.mealsListState.isFailed ->
                _dailyIntakeData.add(DailyIntakeProps.FailedMealsItem)
            state.mealsListState.mealsList.isEmpty() ->
                _dailyIntakeData.add(DailyIntakeProps.EmptyMealsItem)
            state.mealsListState.mealsList.isNotEmpty() ->
                _dailyIntakeData.addAll(
                    state.mealsListState.mealsList.map { it.mapToUiModel() }
                        .toMutableList()
                )
            else ->
                _dailyIntakeData.add(DailyIntakeProps.FailedMealsItem)
        }
        _dailyFragmentProps.postValue(
            DailyFragmentProps(
                _dailyIntakeData,
                ::addMeal,
                ::removeMeal,
                ::moveToFoodList,
                ::moveToCharactersList
            )
        )
    }
}
