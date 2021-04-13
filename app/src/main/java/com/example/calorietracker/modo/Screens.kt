package com.example.calorietracker.modo

import com.example.calorietracker.dailyintake.DailyIntakeFragment
import com.example.calorietracker.foodlist.AddMealDialog
import com.example.calorietracker.foodlist.FoodListFragment
import com.example.calorietracker.graphqltest.characters.CharactersFragment
import com.example.calorietracker.graphqltest.locations.basic.LocationsFragment
import com.example.calorietracker.graphqltest.locations.with_created.LocationsWithCreatedFragment
import com.example.calorietracker.graphqltest.locations.with_type.LocationsWithTypeFragment
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

    @Parcelize
    class CharactersListScreen : AppScreen("CharactersList") {
        override fun create() = CharactersFragment()
    }

    @Parcelize
    class LocationsListScreen : AppScreen("LocationsList") {
        override fun create() = LocationsFragment()
    }

    @Parcelize
    class LocationsWithTypeListScreen : AppScreen("LocationsListWithType") {
        override fun create() = LocationsWithTypeFragment()
    }

    @Parcelize
    class LocationsWithCreatedListScreen : AppScreen("LocationsListWithCreated") {
        override fun create() = LocationsWithCreatedFragment()
    }
}
