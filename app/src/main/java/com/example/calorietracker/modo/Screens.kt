package com.example.calorietracker.modo

import com.example.calorietracker.dailyintake.DailyIntakeFragment
import com.example.calorietracker.foodlist.AddMealDialog
import com.example.calorietracker.foodlist.FoodListFragment
import com.github.terrakok.modo.android.AppScreen
import kotlinx.android.parcel.Parcelize

object Screens {

    @Parcelize
    class DailyIntakeScreen : AppScreen("Start") {
        override fun create() = DailyIntakeFragment()
    }
    @Parcelize
    class FoodListScreen : AppScreen("FoodList") {
        override fun create() = FoodListFragment()
    }
    @Parcelize
    class FoodDialog : AppScreen("FoodDialog") {
        override fun create() = AddMealDialog()
    }
}
