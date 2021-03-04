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

    private val _dailyIntakeData = mutableListOf<DailyIntakeProps>(DailyIntakeProps.LoadingUser, DailyIntakeProps.TextLine, DailyIntakeProps.LoadingMealsItem)

    val dailyLiveData: LiveData<List<DailyIntakeProps>> =
            dailyIntakeRepository.user.combine(dailyIntakeRepository.meals) { userDataSource: UserDataSource.UserState, mealsState: MealsState.FetchedMealsState ->
                val user =
                        when {
                            userDataSource.isLoading ->
//                    _dailyIntakeData[0] = DailyIntakeProps.LoadingUser
                                DailyIntakeProps.LoadingUser
                            userDataSource.isFailed ->
//                    _dailyIntakeData[0] = DailyIntakeProps.FailedUser
                                DailyIntakeProps.FailedUser
                            (userDataSource.fetchedUSer.id != "-1") ->
//                    _dailyIntakeData[0] = userDataSource.fetchedUSer.mapToUiModel()
                                DailyIntakeProps.LoadedUser(userDataSource.fetchedUSer.mapToUiModel()).user
                            else ->
//                    _dailyIntakeData[0] = DailyIntakeProps.FailedUser
                                DailyIntakeProps.FailedUser
                        }
                var meals = mutableListOf<DailyIntakeProps>(DailyIntakeProps.LoadingMealsItem)
                when {
                    mealsState.isLoading ->
                        meals[0] = DailyIntakeProps.LoadingMealsItem
                    mealsState.isFailed ->
                        meals[0] = DailyIntakeProps.FailedMealsItem
                    mealsState.mealsList.isEmpty() ->
                        meals[0] = DailyIntakeProps.FailedMealsItem
                    mealsState.mealsList.isNotEmpty() ->
                        meals = mealsState.mealsList.map { it.mapToUiModel() }.toMutableList()
                    else ->
                        meals[0] = DailyIntakeProps.FailedMealsItem
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
