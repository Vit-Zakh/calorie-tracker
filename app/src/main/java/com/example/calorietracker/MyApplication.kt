package com.example.calorietracker

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.example.calorietracker.state.UserDataSource
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject lateinit var sharedPreferences: SharedPreferences
    @Inject lateinit var userDataSource: UserDataSource

    override fun onCreate() {
        super.onCreate()
        userDataSource.loadCachedUser()
        Log.d("App_TAG", "onCreate: loaded in app class")
    }
}
