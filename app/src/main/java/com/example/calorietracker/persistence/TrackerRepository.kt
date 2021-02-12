package com.example.calorietracker.persistence

import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import retrofit2.HttpException
import javax.inject.Inject

class TrackerRepository @Inject constructor(private val dataSource: DataSource) {

    private val apiClient = com.example.calorietracker.network.RetrofitBuilder
    private val _mealsList = mutableListOf<RecyclerData.Meal>()
    private val _foodList = mutableListOf<RecyclerData.Food>()
    private var _currentUser = RecyclerData.User()

    val intakeDataList: List<RecyclerData>
        get() {
            return mutableListOf(currentUser, RecyclerData.TextLine) + _mealsList
        }

    val foodList: List<RecyclerData.Food> = _foodList

    val currentUser: RecyclerData.User
        get() {
            return RecyclerData.User(
                id = _currentUser.id,
                userName = _currentUser.userName,
                userImage = _currentUser.userImage,
                plannedIntake = _currentUser.plannedIntake,
                userWeight = _currentUser.userWeight,
                userIntake = _currentUser.userIntake
            )
        }

    suspend fun fetchMeals() {
        _mealsList.addAll(loadMealsList())
    }

    suspend fun addMeal(meal: RecyclerData.Meal) {
        _mealsList.add(meal)
    }

    suspend fun removeMeal(index: Int) {
        if (index in 2 until intakeDataList.size)
            _mealsList.removeAt(index)
    }

    suspend fun fetchFood() {
        _foodList.addAll(loadFoodList())
    }

    suspend fun addFood(food: RecyclerData.Food) {
        _foodList.add(food)
    }

    suspend fun removeFood(index: Int) {
        _foodList.removeAt(index)
    }

    suspend fun fetchUser() {
        try {
            _currentUser = apiClient.trackerApiService.getUser()
        } catch (e: HttpException) { // In case of exceeding API-calls limit
            _currentUser.id = "22"
            _currentUser.userName = "Limit Reached"
            _currentUser.userWeight = 22f
            _currentUser.plannedIntake = 40000f
            _currentUser.userImage = "https://cataas.com/cat/cute"
            _currentUser.userIntake = 37000.0
        }
    }

    private suspend fun loadMealsList(): List<RecyclerData.Meal> {
        return try {
            apiClient.trackerApiService.getMeals().meals
        } catch (e: HttpException) { // In case of exceeding API-calls limit
            dataSource.mealList
        }
    }

    private suspend fun loadFoodList(): List<RecyclerData.Food> {
        return try {
            apiClient.trackerApiService.getFoodList().food
        } catch (e: HttpException) { // In case of exceeding API-calls limit
            dataSource.foodList
        }
    }
}
