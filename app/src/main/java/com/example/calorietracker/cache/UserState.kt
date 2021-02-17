package com.example.calorietracker.cache

import com.example.calorietracker.data.RecyclerData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserState @Inject constructor() {
    var cachedUser: RecyclerData.User? = null
}
