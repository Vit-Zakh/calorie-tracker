package com.example.calorietracker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DailyIntakeFragment : Fragment(R.layout.fragment_daily_intake) {

    private val TAG = "DailyIntakeFragment"

    private lateinit var mealsListAdapter: MealsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dailyIntakeList: RecyclerView = view.findViewById(R.id.dailyIntakeList)
        dailyIntakeList.layoutManager = LinearLayoutManager(requireContext())
        mealsListAdapter = MealsListAdapter()
        dailyIntakeList.adapter = mealsListAdapter

        mealsListAdapter.submitList(DataSource.createDataSet())

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            findNavController().navigate(R.id.action_dailyIntakeFragment2_to_foodListFragment2)
        }
    }

    companion object {

        fun getDailyCalories(): String {
            return "%.${2}f".format(
                DataSource.createDataSet().filterIsInstance<Meal>()
                    .sumByDouble { it.mealCalories.times(it.mealWeight.div(100)).toDouble() }
            )
        }
    }
}
