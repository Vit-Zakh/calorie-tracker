package com.example.calorietracker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorietracker.databinding.FragmentDailyIntakeBinding
import kotlin.random.Random

class DailyIntakeFragment : Fragment(R.layout.fragment_daily_intake) {

    private lateinit var mealsListAdapter: MealsListAdapter
    private var fragmentBinding: FragmentDailyIntakeBinding? = null
    private val model: DailyIntakeViewModel by viewModels()
    private lateinit var recyclerData: MutableLiveData<List<RecyclerData>>

    override fun onStart() {
        super.onStart()
        recyclerData = model.getList()
        recyclerData.observe(
            viewLifecycleOwner,
            Observer {
                mealsListAdapter.submitList(it.toList())
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDailyIntakeBinding.bind(view)
        fragmentBinding = binding
        initRecyclerView()

        /** Test buttons block */

//        binding.addMeal.setOnClickListener {
//            model.addMeal(
//                RecyclerData.Meal(
//                    13,
//                    "Popcorn",
//                    "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
//                    490f,
//                    213f
//                )
//            )
// //            model.getList()
//        }

        binding.addMeal.setOnClickListener {
            model.addMeal(
                RecyclerData.Meal(
                    Random.nextInt(20, 1998),
                    "Popcorn",
                    "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
                    490f,
                    213f
                )
            )
        }

        /** End of test buttons block */

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                DailyIntakeFragmentDirections.actionDailyIntakeFragmentToFoodListFragment(
                    getCurrentUser()
                )
            )
        }
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.dailyIntakeList.layoutManager = LinearLayoutManager(requireContext())
            mealsListAdapter = MealsListAdapter()
            it.dailyIntakeList.adapter = mealsListAdapter
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun getCurrentUser(): RecyclerData.User {
        val user = DataSource.getUser()
        user.userIntake = DataSource.getDailyCaloriesValue()
        return user
    }
}
