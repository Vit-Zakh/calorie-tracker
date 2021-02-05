package com.example.calorietracker.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.data.RecyclerData.*
import com.example.calorietracker.databinding.FragmentFoodListBinding
import com.example.calorietracker.utils.RightSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : Fragment() {

    private lateinit var foodListAdapter: FoodListAdapter
    private var fragmentBinding: FragmentFoodListBinding? = null
    private val viewModel: FoodListViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodListBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        initRecyclerView()

        /** Test buttons block */

        binding.addFood.setOnClickListener {
            viewModel.addFood(
                Food(
                    7,
                    "Teriyaki Meat Loaf with something else, and salt and sauce, just to have a really long name here",
                    "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
                    17f
                )
            )
        }

        binding.removeFood.setOnClickListener {
            viewModel.deleteFood(3)
        }

        /** End of test buttons block */

        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.foodGridList.layoutManager =
                StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL)
            foodListAdapter = FoodListAdapter()
            it.foodGridList.adapter = foodListAdapter
            it.foodGridList.addItemDecoration(RightSpacingItemDecoration())
        }
    }

    private fun subscribeObservers() {
        viewModel.foodList.observe(viewLifecycleOwner) {
            refreshFoodList(it.toList())
        }

        viewModel.currentUser.observe(viewLifecycleOwner) {
            setCalorieProgress(it)
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun setCalorieProgress(user: User) {
        val userProgress = user.userIntake / user.plannedIntake
        fragmentBinding?.let {
            it.progressBar.progress = if (userProgress <= 1) {
                ((userProgress) * 70f).toInt()
            } else {
                it.progressPercentText.setTextColor(this.resources.getColor(R.color.design_default_color_error))
                70
            }
            it.progressText.text = resources.getString(
                R.string.user_calories_progress_text,
                user.userIntake,
                user.plannedIntake
            )
            it.progressPercentText.text =
                resources.getString(R.string.user_calories_progress_percent, userProgress * 100)
        }
    }

    private fun refreshFoodList(list: List<Food>) {
        fragmentBinding?.let {
            (it.foodGridList.adapter as FoodListAdapter).submitList(list)
        }
    }
}
