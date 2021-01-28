package com.example.calorietracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {

    private lateinit var mealsListAdapter: MealsListAdapter
    private var fragmentBinding: FragmentFoodListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodListBinding.inflate(inflater, container, false)
        fragmentBinding = binding
        initRecyclerView()
        setCalorieProgress(1347f)
        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.foodGridList.layoutManager =
                StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL)
            mealsListAdapter = MealsListAdapter()
            it.foodGridList.adapter = mealsListAdapter
            it.foodGridList.addItemDecoration(RightSpacingItemDecoration())
            mealsListAdapter.submitList(DataSource.foodList)
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun setCalorieProgress(intake: Float) {
        val userProgress = intake / 2500f
        fragmentBinding?.let {
            it.progressBar.progress = if (userProgress <= 1)
                ((userProgress) * 70f).toInt()
            else 70
            it.progressText.text = "1347 \n out of \n2500"
            it.progressPercentText.text = resources.getString(R.string.user_calories_progress_text, userProgress * 100)
            Log.d("MyTag", "setCalorieProgress: ${(1347f / 2500f) * 70f.toInt()}")
        }
    }
}
