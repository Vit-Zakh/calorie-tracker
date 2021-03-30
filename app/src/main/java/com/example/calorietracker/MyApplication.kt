package com.example.calorietracker

import android.app.Application
import com.example.calorietracker.redux.actions.FetchInitialData
import com.example.calorietracker.redux.store.AppStore
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject lateinit var store: AppStore

    override fun onCreate() {
        super.onCreate()
        store.dispatch(FetchInitialData())
    }
}
