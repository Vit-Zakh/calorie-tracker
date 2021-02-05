package com.example.calorietracker.dailyintake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.databinding.FragmentDailyIntakeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class DailyIntakeFragment : Fragment(R.layout.fragment_daily_intake) {

    private lateinit var dailyIntakeAdapter: DailyIntakeAdapter
    private var fragmentBinding: FragmentDailyIntakeBinding? = null
    private val model: DailyIntakeViewModel by viewModels()
    private lateinit var recyclerData: LiveData<List<RecyclerData>>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerData = model.recyclerData
        recyclerData.observe(
            viewLifecycleOwner,
            {
                dailyIntakeAdapter.submitList(it.toList())
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDailyIntakeBinding.inflate(inflater, container, false)
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

        binding.removeMeal.setOnClickListener {
            model.removeMeal(3)
        }

        /** End of test buttons block */

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                DailyIntakeFragmentDirections.actionDailyIntakeFragmentToFoodListFragment()
            )
        }
        return binding.root
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
}
