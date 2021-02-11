package com.example.calorietracker.persistence

import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import javax.inject.Inject

class TrackerRepository @Inject constructor(private val dataSource: DataSource) {
    private val apiClient = com.example.calorietracker.network.RetrofitBuilder

    val intakeDataList = ArrayList<RecyclerData>()
    val currentUser = RecyclerData.User()

    lateinit var user: RecyclerData.User

    suspend fun addMeal(meal: RecyclerData.Meal) {
        intakeDataList.add(meal)
    }

    suspend fun removeMeal(index: Int) {
        if (index in 2 until intakeDataList.size) // index 0 is user, index 1 is category
            intakeDataList.removeAt(index)
    }

    suspend fun fetchInitialData() {
//        intakeDataList.add(0, currentUser)
        intakeDataList.add(0, RecyclerData.TextLine)
        intakeDataList.add(1, RecyclerData.TextLine)
//        user = apiClient.trackerApiService.getUser()
    }

    suspend fun fetchMeals() {
        intakeDataList.addAll(2, getMeals())
    }

    suspend fun fetchUserData() {
//        val fetchedData = apiClient.trackerApiService.getUser()
//        currentUser.id = fetchedData.id
//        currentUser.userName = fetchedData.userName
//        currentUser.userWeight = fetchedData.userWeight
//        currentUser.plannedIntake = fetchedData.plannedIntake
//        currentUser.userImage = fetchedData.userImage
//        currentUser.userIntake = getMeals().sumByDouble {
//            it.mealCalories.times(it.mealWeight.div(100)).toDouble()
//        }
        intakeDataList[0] =
            RecyclerData.User(
                id = "0",
                userImage = "https://cataas.com/cat/cute",
                userName = "Кошка Машка",
                userWeight = 5.4f,
                plannedIntake = 50000f,
                userIntake = getMeals().sumByDouble {
                    it.mealCalories.times(it.mealWeight.div(100)).toDouble()
                }
            )
    }

    suspend fun getMeals(): List<RecyclerData.Meal> {
        return dataSource.mealList
    }

//    var fetchedData: LiveData<List<RecyclerData>> = liveData {
//        val recyclerList = mutableListOf(RecyclerData.User(), RecyclerData.TextLine)
//        emit(recyclerList)
//        kotlinx.coroutines.delay(2000)
//        val mealsData: MutableList<RecyclerData> = (recyclerList + getMeals()).toMutableList()
//        emit(mealsData)
//        mealsData[0] = fetchUser()
//        kotlinx.coroutines.delay(2000)
//        emit(mealsData)
//    }
}
