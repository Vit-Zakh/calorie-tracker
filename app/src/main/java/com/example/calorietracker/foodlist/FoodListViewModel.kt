package com.example.calorietracker.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val dataSource: DataSource
) : ViewModel() {

    private var foodListData: MutableLiveData<List<RecyclerData.Food>> =
        MutableLiveData<List<RecyclerData.Food>>().apply {
            this.value = dataSource.foodList
        }

    val foodList: LiveData<List<RecyclerData.Food>>
        get() {
            foodListData.value = dataSource.foodList
            return foodListData
        }

    private var user: MutableLiveData<RecyclerData.User> =
        MutableLiveData<RecyclerData.User>().apply {
            this.value = dataSource.currentUser
//            this.value = dataSource.getCurrentUser()
        }

    val currentUser: LiveData<RecyclerData.User>
        get() {
            user.value = dataSource.currentUser
//            user.value = dataSource.getCurrentUser()
            return user
        }

    fun addMealToList(meal: RecyclerData.Meal) {
        dataSource.mealList.add(meal)
        user.value = dataSource.currentUser
//        user.value = dataSource.getCurrentUser()
    }

    fun addFood(food: RecyclerData.Food) {
        dataSource.foodList.add(food)
        foodListData.value = dataSource.foodList
    }

    fun deleteFood(id: Int) {
        dataSource.foodList.removeAt(id)
        foodListData.value = dataSource.foodList
    }
}
