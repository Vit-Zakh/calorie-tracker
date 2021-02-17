package com.example.calorietracker.repositories

import com.example.calorietracker.cache.MealsState
import com.example.calorietracker.cache.UserState
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.network.mapToBusinessModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DailyRepositoryImpl(
    private val mealsState: MealsState,
    private val userState: UserState,
    private val dataSource: DataSource,
    private val apiService: TrackerApiService
) : DailyIntakeRepository {

    override suspend fun fetchData(): Flow<List<RecyclerData>> {
        val initialList = mutableListOf<RecyclerData>(RecyclerData.User(), RecyclerData.TextLine)
        return flow {
            emit(initialList)
            // adding user
            val cachedUser = userState.cachedUser
            if (cachedUser != null) {
                initialList[0] = cachedUser
                emit(initialList)
            }
//            val refreshedUser = RecyclerData.User(
//                id = "0",
//                userImage = "https://cataas.com/cat/cute",
//                userName = "Кошка Машка",
//                userWeight = 5.4f,
//                plannedIntake = 50000f,
//                userIntake = 0.0
//            )
            val refreshedUser = apiService.getUser().mapToBusinessModel()
            initialList[0] = refreshedUser
            userState.cachedUser = refreshedUser
//            kotlinx.coroutines.delay(2000)
            emit(initialList)

            // adding meals
            val cachedMeals = mealsState.cachedMeals
            if (cachedMeals != null) {
                initialList.addAll(cachedMeals)
                emit(initialList)
            }
            val refreshedMeals = apiService.getMeals().mapToBusinessModel()
//            val refreshedMeals = dataSource.mealList
            mealsState.cachedMeals = refreshedMeals.toMutableList()
//            delay(4000)
            initialList.addAll(refreshedMeals)
            emit(initialList)
        }
    }

    override suspend fun addMeal(meal: RecyclerData.Meal) {
        TODO("Not yet implemented")
    }

    override suspend fun removeMeal(index: Int) {
        TODO("Not yet implemented")
    }
}
