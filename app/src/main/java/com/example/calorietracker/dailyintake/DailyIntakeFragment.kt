package com.example.calorietracker.dailyintake

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.RecyclerData
import com.example.calorietracker.databinding.FragmentDailyIntakeBinding
import kotlin.random.Random

class DailyIntakeFragment : Fragment(R.layout.fragment_daily_intake) {

    private lateinit var dailyIntakeAdapter: DailyIntakeAdapter
    private var fragmentBinding: FragmentDailyIntakeBinding? = null
    private val model: DailyIntakeViewModel by viewModels()
    private lateinit var recyclerData: MutableLiveData<List<RecyclerData>>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerData = model.getList()
        recyclerData.observe(
            viewLifecycleOwner,
            Observer {
                dailyIntakeAdapter.submitList(it.toList())
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDailyIntakeBinding.bind(view)
        fragmentBinding = binding
        initRecyclerView()

        /** Test buttons block */

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
            dailyIntakeAdapter = DailyIntakeAdapter()
            it.dailyIntakeList.adapter = dailyIntakeAdapter
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun getCurrentUser(): RecyclerData.User {
        return model.dataSource.getCurrentUser()
    }
}
