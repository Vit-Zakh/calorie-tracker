package com.example.calorietracker.repositories

import com.example.calorietracker.cache.FoodListState
import com.example.calorietracker.cache.UserState
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.network.mapToBusinessModel
import kotlinx.coroutines.flow.Flow

class FoodListRepositoryImpl(
    private val userState: UserState,
    private val foodListState: FoodListState,
    private val dataSource: DataSource,
    private val apiService: TrackerApiService
) : FoodListRepository {

    override suspend fun fetchUser(): Flow<RecyclerData.User> {
        return userState.userFlow
    }

    override suspend fun fetchFood(): Flow<List<RecyclerData.Food>> {
        return foodListState.foodListFlow
    }

    override suspend fun addFood(food: RecyclerData.Food) {
        foodListState.cachedFoodList.add(food)
    }

    override suspend fun deleteFood(index: Int) {
        foodListState.cachedFoodList.removeAt(index)
    }

    override suspend fun refreshFood() {
//        delay(4000)
//        foodListState.cachedFoodList.addAll(dataSource.foodList)
        foodListState.cachedFoodList.addAll(apiService.getFoodList().mapToBusinessModel())
    }

    override suspend fun refreshUser() {
//        delay(5000)
//        with(userState.cachedUser) {
//            this.id = "0"
//            this.userName = "Кошка Машка"
//            this.userImage = "https://cataas.com/cat/cute"
//            this.userWeight = 5.4f
//            this.plannedIntake = 50000f
//            this.userIntake = 0.0
//        }
        val refreshedUser = apiService.getUser().mapToBusinessModel()
        with(userState.cachedUser) {
            this.id = refreshedUser.id
            this.userName = refreshedUser.userName
            this.userImage = refreshedUser.userImage
            this.userWeight = refreshedUser.userWeight
            this.plannedIntake = refreshedUser.plannedIntake
            this.userIntake = refreshedUser.userIntake
        }
    }
}
