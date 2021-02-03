package com.example.calorietracker

import androidx.lifecycle.ViewModel

class FoodListViewModel : ViewModel() {

//    private var foodListData: MutableLiveData<List<RecyclerData.Food>> = loadFoodData()

//    private val recyclerData: MutableLiveData<List<RecyclerData>> by lazy {
//        MutableLiveData<List<RecyclerData>>().also {
//            loadData()
//        }
//    }

//    fun getFoodList(): MutableLiveData<List<RecyclerData.Food>> {
//        return foodListData
//    }

    fun addFood(food: RecyclerData.Food) {
//        DataSource.foodList.add(food)
//        foodListData.value = DataSource.foodList
    }

//    private fun loadFoodData(): MutableLiveData<List<RecyclerData.Food>> {
// //        val liveDataFoodList = MutableLiveData<List<RecyclerData.Food>>()
// //        liveDataFoodList.value = DataSource.foodList
// //        return liveDataFoodList
// //    }
}
