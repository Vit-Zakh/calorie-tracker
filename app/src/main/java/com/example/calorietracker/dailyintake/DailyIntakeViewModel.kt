package com.example.calorietracker.dailyintake

import androidx.lifecycle.*
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dailyIntakeRepository: DailyIntakeRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            dailyIntakeRepository.refreshState()
        }
    }

    val dailyLiveData: LiveData<List<DailyIntakeProps>> =
        dailyIntakeRepository.user.combine(dailyIntakeRepository.meals) { userDataSource: UserDataSource.UserState, mealsState: MealsState.FetchedMealsState ->
            val _dailyIntakeData = mutableListOf<DailyIntakeProps>()
            val user =
                when {
                    userDataSource.isLoading ->
                        DailyIntakeProps.LoadingUser
                    userDataSource.isFailed ->
                        DailyIntakeProps.FailedUser
                    userDataSource.fetchedUSer.id.isNotBlank() ->
                        DailyIntakeProps.LoadedUser(userDataSource.fetchedUSer.mapToUiModel()).user
                    else ->
                        DailyIntakeProps.FailedUser
                }
            _dailyIntakeData.add(user)
            _dailyIntakeData.add(DailyIntakeProps.TextLine)
            when {
                mealsState.isLoading ->
                    _dailyIntakeData.add(DailyIntakeProps.LoadingMealsItem)
                mealsState.isFailed ->
                    _dailyIntakeData.add(DailyIntakeProps.FailedMealsItem)
                mealsState.mealsList.isEmpty() ->
                    _dailyIntakeData.add(DailyIntakeProps.EmptyMealsItem)
                mealsState.mealsList.isNotEmpty() ->
                    _dailyIntakeData.addAll(mealsState.mealsList.map { it.mapToUiModel() }.toMutableList())
                else ->
                    _dailyIntakeData.add(DailyIntakeProps.FailedMealsItem)
            }
            _dailyIntakeData
        }.asLiveData()

    fun addMeal(mealProps: DailyIntakeProps.MealProps) {
        dailyIntakeRepository.addMeal(mealProps)
    }

    fun removeMeal(index: Int) {
        dailyIntakeRepository.deleteMeal(index)
    }
}
