package com.example.calorietracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyIntakeViewModel() : ViewModel() {

    val dataSource = DataSource()
    private var recyclerData: MutableLiveData<List<RecyclerData>> = loadData()

//    private val recyclerData: MutableLiveData<List<RecyclerData>> by lazy {
//        MutableLiveData<List<RecyclerData>>().also {
//            loadData()
//        }
//    }

    fun getList(): MutableLiveData<List<RecyclerData>> {
        return recyclerData
    }

    fun addMeal(meal: RecyclerData.Meal) {
        dataSource.mealList.add(0, meal)
        recyclerData.value = dataSource.getDataList()
    }

    private fun loadData(): MutableLiveData<List<RecyclerData>> {
        val liveDataList = MutableLiveData<List<RecyclerData>>()
        liveDataList.value = dataSource.getDataList()
        return liveDataList
    }
}
