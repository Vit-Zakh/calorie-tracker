package com.example.calorietracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyIntakeViewModel() : ViewModel() {
//    private val recyclerData: MutableLiveData<List<RecyclerData>> by lazy {
//        MutableLiveData<List<RecyclerData>>().also {
//            loadData()
//        }
//    }

    private val recyclerData: MutableLiveData<List<RecyclerData>> = DataSource.getLiveData()

    fun getList(): MutableLiveData<List<RecyclerData>> {
        return recyclerData
    }

    fun addMeal(meal: RecyclerData.Meal) {
        DataSource.list.add(meal)
    }

//    private fun loadData() {
//        DataSource.getLiveData()
//    }
}
