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

    private var _foodList: MutableLiveData<List<RecyclerData.Food>> =
        MutableLiveData<List<RecyclerData.Food>>().apply {
            this.value = dataSource.foodList
        }

    val foodList: LiveData<List<RecyclerData.Food>>
        get() {
            _foodList.value = dataSource.foodList
            return _foodList
        }

    private var _currentUser: MutableLiveData<RecyclerData.User> =
        MutableLiveData<RecyclerData.User>().apply {
            this.value = dataSource.user
        }

    val currentUser: LiveData<RecyclerData.User>
        get() {
            _currentUser.value = dataSource.user
            return _currentUser
        }

    fun addMealToList(meal: RecyclerData.Meal) {
        dataSource.mealList.add(meal)
        _currentUser.value = dataSource.user
    }

    fun addFood(food: RecyclerData.Food) {
        dataSource.foodList.add(food)
        _foodList.value = dataSource.foodList
    }

    fun deleteFood(id: Int) {
        dataSource.foodList.removeAt(id)
        _foodList.value = dataSource.foodList
    }
}
