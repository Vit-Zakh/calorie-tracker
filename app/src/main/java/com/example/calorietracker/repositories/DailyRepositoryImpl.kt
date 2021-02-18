package com.example.calorietracker.repositories

import com.example.calorietracker.cache.DailyIntakeState
import com.example.calorietracker.cache.MealsState
import com.example.calorietracker.cache.UserState
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.TrackerApiService

class DailyRepositoryImpl(
    private val mealsState: MealsState,
    private val userState: UserState,
    private val dailyIntakeState: DailyIntakeState,
    private val dataSource: DataSource,
    private val apiService: TrackerApiService
) : DailyIntakeRepository {

    override val user = userState.cashedUser
    override val meals = mealsState.cashedMealsList

    override suspend fun refreshState() {

//        userState.refreshUser(apiService.getUser())
//        mealsState.refreshMealsList(apiService.getMeals())

//        delay(1000)
//        dailyIntakeState.initialList[0] = RecyclerData.User(
//            id = "0",
//            userImage = "https://cataas.com/cat/cute",
//            userName = "Кошка Машка",
//            userWeight = 5.4f,
//            plannedIntake = 50000f,
//            userIntake = 0.0
//        )
//        delay(7000)
//        dailyIntakeState.initialList.addAll(dataSource.mealList)
//        firstBootState.initialList[0] = apiService.getUser().mapToBusinessModel()
//        firstBootState.initialList.addAll(apiService.getMeals().mapToBusinessModel())
    }

    override suspend fun addMeal(meal: RecyclerData.Meal) {
        dailyIntakeState.initialList.add(meal)
    }

    override suspend fun removeMeal(index: Int) {
        dailyIntakeState.initialList.removeAt(index)
    }
}
