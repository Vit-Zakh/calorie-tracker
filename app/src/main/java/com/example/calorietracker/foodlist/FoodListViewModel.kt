package com.example.calorietracker.foodlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.DataSource
import com.example.calorietracker.RecyclerData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val dataSource: DataSource
) : ViewModel() {

//    private val dataSource = DataSource()
    private var foodListData: MutableLiveData<List<RecyclerData.Food>> = loadFoodData()
    private val currentUser: MutableLiveData<RecyclerData.User> = loadCurrentUser()

//    private val foodListData: MutableLiveData<List<RecyclerData.Food>> by lazy {
//        MutableLiveData<List<RecyclerData.Food>>().also {
//            loadFoodData()
//        }
//    }

    fun getFoodList(): MutableLiveData<List<RecyclerData.Food>> {
        return foodListData
    }

    fun getCurrentUser(): MutableLiveData<RecyclerData.User> {
        return currentUser
    }

    fun addMealToList(meal: RecyclerData.Meal) {
        dataSource.mealList.add(meal)
        currentUser.value = dataSource.getCurrentUser()
    }

    fun addFood(food: RecyclerData.Food) {
        dataSource.foodList.add(food)
        foodListData.value = dataSource.foodList
    }

    private fun loadFoodData(): MutableLiveData<List<RecyclerData.Food>> {
        val liveDataFoodList = MutableLiveData<List<RecyclerData.Food>>()
        liveDataFoodList.value = dataSource.foodList
        return liveDataFoodList
    }

    private fun loadCurrentUser(): MutableLiveData<RecyclerData.User> {
        val liveDataUser = MutableLiveData<RecyclerData.User>()
        liveDataUser.value = dataSource.getCurrentUser()
        return liveDataUser
    }
}
