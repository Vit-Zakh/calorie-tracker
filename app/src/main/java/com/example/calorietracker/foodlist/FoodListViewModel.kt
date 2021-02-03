package com.example.calorietracker.foodlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.DataSource
import com.example.calorietracker.RecyclerData

class FoodListViewModel : ViewModel() {

    val dataSource = DataSource()
    private var foodListData: MutableLiveData<List<RecyclerData.Food>> = loadFoodData()

//    private val foodListData: MutableLiveData<List<RecyclerData.Food>> by lazy {
//        MutableLiveData<List<RecyclerData.Food>>().also {
//            loadFoodData()
//        }
//    }

    fun getFoodList(): MutableLiveData<List<RecyclerData.Food>> {
        return foodListData
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
}
