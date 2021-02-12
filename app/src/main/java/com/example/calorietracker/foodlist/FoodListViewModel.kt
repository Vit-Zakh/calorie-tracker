package com.example.calorietracker.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.persistence.TrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    dataSource: DataSource
) : ViewModel() {

    private val repository = TrackerRepository(dataSource)

    private var _foodList: MutableLiveData<List<RecyclerData.Food>> =
        MutableLiveData<List<RecyclerData.Food>>().apply {
            this.value = repository.foodList
        }

    val foodList: LiveData<List<RecyclerData.Food>> = _foodList

    private var _currentUser: MutableLiveData<RecyclerData.User> =
        MutableLiveData<RecyclerData.User>().apply {
            this.value = repository.currentUser
        }

    val currentUser: LiveData<RecyclerData.User> = _currentUser

    init {
        viewModelScope.launch {
            repository.fetchFood()
            _foodList.value = repository.foodList
        }
        viewModelScope.launch {
            repository.fetchUser()
            _currentUser.value = repository.currentUser
        }
    }

    fun addMealToList(meal: RecyclerData.Meal) {
        viewModelScope.launch {
            repository.addMeal(meal)
            _currentUser.value = repository.currentUser
        }
    }

    fun addFood(food: RecyclerData.Food) {
        viewModelScope.launch {
            repository.addFood(food)
            _foodList.value = repository.foodList
        }
    }

    fun deleteFood(id: Int) {
        viewModelScope.launch {
            repository.removeFood(id)
            _foodList.value = repository.foodList
        }
    }
}
