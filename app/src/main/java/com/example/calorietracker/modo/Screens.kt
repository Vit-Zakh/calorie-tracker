package com.example.calorietracker.modo

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.calorietracker.dailyintake.DailyIntakeFragment
import com.example.calorietracker.foodlist.AddMealDialog
import com.example.calorietracker.foodlist.FoodListFragment
import com.example.calorietracker.graphqltest.characters.CharactersFragment
import com.example.calorietracker.graphqltest.locations.LocationsFragment
import com.github.terrakok.modo.android.AppScreen
import kotlinx.android.parcel.Parcelize

object Screens {

    inline fun <reified T : Fragment> newFragmentInstance(vararg params: Pair<String, String>) =
        T::class.java.newInstance().apply {
            arguments = bundleOf(*params)
        }

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
    class LocationsListScreen : AppScreen("Raw Locations") {
        override fun create(): LocationsFragment = newFragmentInstance("id" to "Raw Locations")
    }

    @Parcelize
    class LocationsWithTypeListScreen : AppScreen("Locations With Type") {
        override fun create(): LocationsFragment = newFragmentInstance("id" to "Locations With Type")
    }

    @Parcelize
    class LocationsWithCreatedListScreen : AppScreen("Locations With Created") {
        override fun create(): LocationsFragment = newFragmentInstance("id" to "Locations With Created")
    }
}
