package com.example.calorietracker.graphqltest.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CustomViewModelProvider {
    fun create(initParams: ViewModelParams): JointLocationsViewModel
}

object CustomViewModelFactory {
    fun provideFactory(
        assistedFactory: CustomViewModelProvider,
        initParams: ViewModelParams
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(initParams) as T
        }
    }
}
