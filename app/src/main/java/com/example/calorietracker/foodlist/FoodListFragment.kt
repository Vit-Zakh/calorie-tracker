package com.example.calorietracker.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentFoodListBinding
import com.example.calorietracker.models.ui.DailyIntakeProps.*
import com.example.calorietracker.models.ui.FoodListProps.*
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.utils.RightSpacingItemDecoration
import com.example.calorietracker.utils.showIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_food_list.*
import kotlin.random.Random

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
        binding.lifecycleOwner = viewLifecycleOwner
        fragmentBinding = binding

        initRecyclerView()

        /** Test buttons block */

        binding.addFood.setOnClickListener {
            viewModel.addFood(
                FoodProps(
                    Random.nextInt(9, 2000).toString(),
                    "Teriyaki Meat Loaf with something else, and salt and sauce, just to have a really long name here",
                    "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
                    17f
                )
            )
        }

        binding.removeFood.setOnClickListener {
            viewModel.deleteFood(3)
        }

        binding.empty.setOnClickListener {
            showEmptyListMessage()
        }

        binding.error.setOnClickListener {
            showFailedListMessage()
        }

        /** End of test buttons block */

        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.foodGridList.layoutManager =
                StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL)
            foodListAdapter = FoodListAdapter { food ->
                openAddMealDialog(food)
            }
            it.foodGridList.adapter = foodListAdapter
            it.foodGridList.addItemDecoration(RightSpacingItemDecoration())
        }
    }

    private fun subscribeObservers() {
        viewModel.currentUserData.observe(viewLifecycleOwner) { userData ->

            fragmentBinding?.let {
                it.progressPercentText.showIf(userData != FailedUser)
                it.progressText.showIf(userData != FailedUser)
                it.progressBar.showIf(userData != FailedUser)
                it.progressBarContainer.showIf(userData != FailedUser)
                it.textView.showIf(userData != FailedUser)
                it.failedListMessage.showIf(userData is FailedUser)
                it.failedListImage.showIf(userData is FailedUser)
            }

            when (userData) {
                is LoadedUser -> updateUserProgress(userData.user.copy())
                is LoadingUser -> fragmentBinding?.progressPercentText?.text = "Refreshing..."
                is FailedUser -> {
                    Toast.makeText(context, "Failed to update user data", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.foodListData.observe(viewLifecycleOwner) { listData ->
            fragmentBinding?.let {
                it.foodListProgressBar.showIf(listData is LoadingFoodList)
                it.emptyListMessage.showIf(listData is EmptyFoodList)
                it.failedListMessage.showIf(listData is FailedFoodList)
                it.failedListMessage.showIf(listData is FailedFoodList)
            }
            when (listData) {
                is LoadedFoodList -> (fragmentBinding?.foodGridList?.adapter as FoodListAdapter).submitList(listData.foodList.toList())
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun updateUserProgress(user: UserProps) {
        fragmentBinding?.let {
            val userProgress = user.userIntake / user.plannedIntake
            it.progressBar.progress = progressOutOfValue(userProgress)
            it.progressText.text = resources.getString(
                R.string.user_calories_progress_text,
                user.userIntake,
                user.plannedIntake
            )
            it.progressPercentText.text = resources.getString(R.string.user_calories_progress_percent, userProgress * 100)
            if (userProgress > 1.0) {
                it.progressPercentText.setTextColor(resources.getColor(R.color.design_default_color_error))
            }
        }
    }

    private fun openAddMealDialog(food: FoodProps) {
        findNavController().navigate(
            FoodListFragmentDirections.actionFoodListFragmentToAddMealDialog(
                food
            )
        )
    }

    /** Test functions block */

    private fun showEmptyListMessage() {
        fragmentBinding?.let {
            it.progressBar.visibility = VISIBLE
            it.progressBarContainer.visibility = VISIBLE
            it.progressText.visibility = VISIBLE
            it.progressPercentText.visibility = VISIBLE
            it.emptyListMessage.visibility = VISIBLE
            it.emptyListImage.visibility = VISIBLE
            it.failedListMessage.visibility = GONE
            it.failedListImage.visibility = GONE
            it.foodGridList.visibility = GONE
            it.textView.visibility = GONE
        }
    }

    private fun showFailedListMessage() {
        fragmentBinding?.let {
            it.progressBar.visibility = GONE
            it.progressBarContainer.visibility = GONE
            it.progressText.visibility = GONE
            it.progressPercentText.visibility = GONE
            it.foodListProgressBar.visibility = GONE
            it.emptyListMessage.visibility = GONE
            it.emptyListImage.visibility = GONE
            it.failedListMessage.visibility = VISIBLE
            it.failedListImage.visibility = VISIBLE
            it.foodGridList.visibility = GONE
            it.textView.visibility = GONE
        }
    }

    /** End of test functions block */

    private fun progressOutOfValue(value: Double): Int {
        return if (value <= 1.0) {
            ((value) * 70.0).toInt()
        } else {
            70
        }
    }
}
