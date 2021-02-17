package com.example.calorietracker.cache

import com.example.calorietracker.data.RecyclerData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirstBootState @Inject constructor() {
    val initialList = mutableListOf<RecyclerData>(RecyclerData.User(), RecyclerData.TextLine)
}
