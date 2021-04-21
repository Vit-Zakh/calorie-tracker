package com.example.calorietracker.graphqltest.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calorietracker.redux.store.AppStore

class CustomViewModelProvider(
    private val id: String,
    private val store: AppStore
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return JointLocationsViewModel(store = store, id = id) as T
    }
}
