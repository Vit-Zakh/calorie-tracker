package com.example.calorietracker.repositories

import com.example.calorietracker.cache.FoodListState
import com.example.calorietracker.cache.UserState
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.network.mapToBusinessModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FoodListRepositoryImpl(
    private val userState: UserState,
    private val foodListState: FoodListState,
    private val dataSource: DataSource,
    private val apiService: TrackerApiService
) : FoodListRepository {

    override suspend fun fetchUser(): Flow<RecyclerData.User> {
        return flow {
            if (userState.cachedUser != null) userState.cachedUser?.let { emit(it) }
            updateUserCache()
//            kotlinx.coroutines.delay(2000)
            userState.cachedUser?.let { emit(it) }
        }
    }

    override suspend fun fetchFood(): Flow<List<RecyclerData.Food>> {
        return flow {
            val cachedFoodList = foodListState.cachedFoodList
            if (cachedFoodList != null) emit(cachedFoodList)
//            val refreshedFoodList = dataSource.foodList
//            delay(2000)
            val refreshedFoodList = apiService.getFoodList().mapToBusinessModel()
            emit(refreshedFoodList)
        }
    }

    override suspend fun addFood(food: RecyclerData.Food) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFood(index: Int) {
        TODO("Not yet implemented")
    }

    suspend fun updateUserCache() {
//        userState.cachedUser = RecyclerData.User(
//            id = "0",
//            userImage = "https://cataas.com/cat/cute",
//            userName = "Кошка Машка",
//            userWeight = 5.4f,
//            plannedIntake = 50000f,
//            userIntake = 0.0
//        )
        userState.cachedUser = apiService.getUser().mapToBusinessModel()
    }
}
