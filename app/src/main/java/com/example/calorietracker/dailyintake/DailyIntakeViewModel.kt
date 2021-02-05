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

    private var _recyclerData: MutableLiveData<List<RecyclerData>> =
        MutableLiveData<List<RecyclerData>>().apply {
            this.value = dataSource.dataList
        }

    val recyclerData: LiveData<List<RecyclerData>>
        get() {
            _recyclerData.value = dataSource.dataList
            return _recyclerData
        }

    fun addMeal(meal: RecyclerData.Meal) {
        dataSource.mealList.add(0, meal)
        _recyclerData.value = dataSource.dataList
    }

    fun removeMeal(id: Int) {
        dataSource.mealList.removeAt(id)
        _recyclerData.value = dataSource.dataList
    }
}
