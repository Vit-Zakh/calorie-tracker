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

    private var _dailyIntakeData: MutableLiveData<List<RecyclerData>> =
        MutableLiveData<List<RecyclerData>>().apply {
            this.value = dataSource.dataList
        }

    val dailyIntakeData: LiveData<List<RecyclerData>> = _dailyIntakeData
        get() {
            _dailyIntakeData.value = dataSource.dataList
            return _dailyIntakeData
        }

    fun addMeal(meal: RecyclerData.Meal) {
        dataSource.mealList.add(0, meal)
        _dailyIntakeData.value = dataSource.dataList
    }

    fun removeMeal(id: Int) {
        dataSource.mealList.removeAt(id)
        _dailyIntakeData.value = dataSource.dataList
    }
}
