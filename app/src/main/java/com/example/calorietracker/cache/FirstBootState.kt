package com.example.calorietracker.cache

import com.example.calorietracker.data.RecyclerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirstBootState @Inject constructor() {
    val initialList = mutableListOf<RecyclerData>(RecyclerData.User(), RecyclerData.TextLine)
    val intakeDataFlow: Flow<List<RecyclerData>> = flow {
        while (true) {
            kotlinx.coroutines.delay(5000)
            emit(initialList)
        }
    }
}
