package com.example.calorietracker.repositories

import com.example.calorietracker.cache.FirstBootState
import com.example.calorietracker.cache.MealsState
import com.example.calorietracker.cache.UserState
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.network.mapToBusinessModel
import kotlinx.coroutines.flow.Flow

class DailyRepositoryImpl(
    private val mealsState: MealsState,
    private val userState: UserState,
    private val firstBootState: FirstBootState,
    private val dataSource: DataSource,
    private val apiService: TrackerApiService
) : DailyIntakeRepository {

    override suspend fun fetchData(): Flow<List<RecyclerData>> {
        return firstBootState.intakeDataFlow
    }

    override suspend fun refreshState() {
//        delay(1000)
//        firstBootState.initialList[0] = RecyclerData.User(
//            id = "0",
//            userImage = "https://cataas.com/cat/cute",
//            userName = "Кошка Машка",
//            userWeight = 5.4f,
//            plannedIntake = 50000f,
//            userIntake = 0.0
//        )
//        delay(7000)
//        firstBootState.initialList.addAll(dataSource.mealList)
        firstBootState.initialList[0] = apiService.getUser().mapToBusinessModel()
        firstBootState.initialList.addAll(apiService.getMeals().mapToBusinessModel())
    }

    override suspend fun addMeal(meal: RecyclerData.Meal) {
        firstBootState.initialList.add(meal)
    }

    override suspend fun removeMeal(index: Int) {
        firstBootState.initialList.removeAt(index)
    }
}
