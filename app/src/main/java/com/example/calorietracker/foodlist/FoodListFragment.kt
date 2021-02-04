package com.example.calorietracker.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.RecyclerData
import com.example.calorietracker.RecyclerData.*
import com.example.calorietracker.RightSpacingItemDecoration
import com.example.calorietracker.databinding.FragmentFoodListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : Fragment() {

    private lateinit var foodListAdapter: FoodListAdapter
    private var fragmentBinding: FragmentFoodListBinding? = null
    private val args: FoodListFragmentArgs by navArgs()
    private val model: FoodListViewModel by activityViewModels()
    private lateinit var recyclerData: MutableLiveData<List<RecyclerData.Food>>
    private lateinit var currentUser: MutableLiveData<User>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodListBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        subscribeObservers()
        initRecyclerView()

        binding.addFood.setOnClickListener {
            model.addFood(
                Food(
                    7,
                    "Popcorn",
                    "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
                    17f
                )
            )
        }

        binding.removeFood.setOnClickListener {
            model.addFood(
                Food(
                    7,
                    "Teriyaki Meat Loaf with something else, and salt and sauce, just to have a really long name here",
                    "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
                    17f
                )
            )
        }

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
        recyclerData = model.getFoodList()
        currentUser = model.getCurrentUser()

        recyclerData.observe(
            viewLifecycleOwner,
            { foodListAdapter.submitList(it.toList()) }
        )

        currentUser.observe(
            viewLifecycleOwner,
            { setCalorieProgress(it) }
        )
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
                it.progressPercentText.setTextColor(R.color.design_default_color_error)
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
}
