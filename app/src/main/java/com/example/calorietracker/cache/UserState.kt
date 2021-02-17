package com.example.calorietracker.cache

import com.example.calorietracker.data.RecyclerData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserState @Inject constructor() {
    val cachedUser = RecyclerData.User()
    val userFlow: Flow<RecyclerData.User> = flow {
        while (true) {
            emit(cachedUser)
            delay(3000)
        }
    }
}
