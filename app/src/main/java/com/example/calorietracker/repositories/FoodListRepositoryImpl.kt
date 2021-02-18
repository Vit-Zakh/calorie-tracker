package com.example.calorietracker.repositories

import com.example.calorietracker.cache.DailyIntakeState
import com.example.calorietracker.cache.FoodListState
import com.example.calorietracker.cache.UserState
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.FoodListResponse
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow

class FoodListRepositoryImpl(
    private val userState: UserState,
    private val foodListState: FoodListState,
    private val dailyIntakeState: DailyIntakeState,
    private val dataSource: DataSource,
    private val apiService: TrackerApiService
) : FoodListRepository {

    override val user: MutableStateFlow<UserResponse> = userState.cashedUser
    override val food: MutableStateFlow<FoodListResponse> = foodListState.cashedFoodList

    override suspend fun addFood(food: RecyclerData.Food) {
    }

    override suspend fun deleteFood(index: Int) {
    }

    override suspend fun addMealToList(meal: RecyclerData.Meal) {
    }

    override suspend fun refreshFood() {
        foodListState.refreshFoodList(apiService.getFoodList())

//        delay(4000)

//        foodListState.cachedFoodList.addAll(apiService.getFoodList().mapToBusinessModel())
    }

    override suspend fun refreshUser() {
        userState.refreshUser(apiService.getUser())

//        delay(5000)
//        with(userState.cachedUser) {
//            this.id = "0"
//            this.userName = "Кошка Машка"
//            this.userImage = "https://cataas.com/cat/cute"
//            this.userWeight = 5.4f
//            this.plannedIntake = 50000f
//            this.userIntake = 0.0
//        }
//        val refreshedUser = apiService.getUser().mapToBusinessModel()
//        with(userState.cachedUser) {
//            this.id = refreshedUser.id
//            this.userName = refreshedUser.userName
//            this.userImage = refreshedUser.userImage
//            this.userWeight = refreshedUser.userWeight
//            this.plannedIntake = refreshedUser.plannedIntake
//            this.userIntake = refreshedUser.userIntake
//        }
    }
}
