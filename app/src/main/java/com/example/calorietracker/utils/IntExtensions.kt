package com.example.calorietracker.utils

fun Int.calculateProgressPercent(currentValue: Double, plannedValue: Float): Int {
    return (currentValue / plannedValue * 100).toInt()
}
