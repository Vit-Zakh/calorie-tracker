package com.example.calorietracker.dailyintake

import androidx.lifecycle.*
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserState
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
        dailyIntakeRepository.user.combine(dailyIntakeRepository.meals) { userState: UserState.FetchedUserState, mealsState: MealsState.FetchedMealsState ->
            val user =
                when {
                    userState.isLoading -> DailyIntakeProps.LoadingUser
                    userState.isFailed -> DailyIntakeProps.FailedUser
                    (userState.fetchedUSer.id != "-1") -> DailyIntakeProps.LoadedUser(userState.fetchedUSer.mapToUiModel())
                    else -> DailyIntakeProps.FailedUser
                }
            val meals =
                when {
                    mealsState.isLoading -> listOf(DailyIntakeProps.LoadingMealsItem)
                    mealsState.isFailed -> listOf(DailyIntakeProps.FailedMealsItem)
                    mealsState.mealsList.isEmpty() -> listOf(DailyIntakeProps.EmptyMealsItem)
                    mealsState.mealsList.isNotEmpty() -> DailyIntakeProps.LoadedMealsList(mealsState.mealsList.map { it.mapToUiModel() }).mealsList
                    else -> listOf(DailyIntakeProps.FailedMealsItem)
                }
            listOf(user, DailyIntakeProps.TextLine) + meals
        }.asLiveData()

    fun addMeal(mealProps: DailyIntakeProps.MealProps) {
        dailyIntakeRepository.addMeal(mealProps)
    }

    fun removeMeal(index: Int) {
        dailyIntakeRepository.deleteMeal(index)
    }
}
