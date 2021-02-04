package com.example.calorietracker.dailyintake

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyIntakeViewModel @Inject constructor(
    private val dataSource: DataSource
) : ViewModel() {

    private var recyclerData: MutableLiveData<List<RecyclerData>> = loadData()

//    private val recyclerData: MutableLiveData<List<RecyclerData>> by lazy {
//        MutableLiveData<List<RecyclerData>>().also {
//            loadData()
//        }
//    }

    fun getList(): MutableLiveData<List<RecyclerData>> {
        recyclerData.value = dataSource.getDataList()
        return recyclerData
    }

    fun addMeal(meal: RecyclerData.Meal) {
        dataSource.mealList.add(0, meal)
        recyclerData.value = dataSource.getDataList()
    }

    fun removeMeal(id: Int) {
        dataSource.mealList.removeAt(id)
        recyclerData.value = dataSource.getDataList()
    }

    private fun loadData(): MutableLiveData<List<RecyclerData>> {
        val liveDataList = MutableLiveData<List<RecyclerData>>()
        liveDataList.value = dataSource.getDataList()
        return liveDataList
    }

    fun getCurrentUser(): RecyclerData.User {
        return dataSource.getCurrentUser()
    }
}
