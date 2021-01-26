package com.example.calorietracker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.databinding.FragmentDailyIntakeBinding

class DailyIntakeFragment : Fragment(R.layout.fragment_daily_intake) {

    private lateinit var mealsListAdapter: MealsListAdapter
    private var fragmentBinding: FragmentDailyIntakeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDailyIntakeBinding.bind(view)
        fragmentBinding = binding
        initRecyclerView()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_dailyIntakeFragment_to_foodListFragment)
        }
    }

    private fun initRecyclerView() {
        val dailyIntakeList: RecyclerView = fragmentBinding!!.dailyIntakeList
        dailyIntakeList.layoutManager = LinearLayoutManager(requireContext())
        mealsListAdapter = MealsListAdapter()
        dailyIntakeList.adapter = mealsListAdapter
        mealsListAdapter.submitList(DataSource.getDataSet())
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}
