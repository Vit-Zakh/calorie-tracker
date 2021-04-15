package com.example.calorietracker

import android.app.Application
import com.example.calorietracker.redux.actions.FetchInitialData
import com.example.calorietracker.redux.store.AppStore
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.android.AppReducer
import com.github.terrakok.modo.android.LogReducer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject
    lateinit var store: AppStore

    override fun onCreate() {
        modo = Modo(LogReducer(AppReducer(this)))
        super.onCreate()
        store.dispatch(FetchInitialData())
    }

    companion object {
        lateinit var modo: Modo
            private set
    }
}
