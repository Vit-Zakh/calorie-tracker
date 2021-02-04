package com.example.calorietracker.dailyintake

import androidx.lifecycle.LiveData
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

    private var recyclerData: MutableLiveData<List<RecyclerData>> =
        MutableLiveData<List<RecyclerData>>().apply {
            this.value = dataSource.recyclerDataList
        }

    val recyclerDataList: LiveData<List<RecyclerData>>
        get() {
            recyclerData.value = dataSource.recyclerDataList
            return recyclerData
        }

    fun addMeal(meal: RecyclerData.Meal) {
        dataSource.mealList.add(0, meal)
        recyclerData.value = dataSource.recyclerDataList
    }

    fun removeMeal(id: Int) {
        dataSource.mealList.removeAt(id)
        recyclerData.value = dataSource.recyclerDataList
    }
}
