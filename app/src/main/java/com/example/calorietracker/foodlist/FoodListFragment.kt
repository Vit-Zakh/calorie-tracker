package com.example.calorietracker.foodlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentFoodListBinding
import com.example.calorietracker.models.ui.DailyIntakeProps.FailedUser
import com.example.calorietracker.models.ui.DailyIntakeProps.UserProps
import com.example.calorietracker.models.ui.FoodListProps.*
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.utils.RightSpacingItemDecoration
import com.example.calorietracker.utils.showIf
import dagger.hilt.android.AndroidEntryPoint
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

        binding.empty.setOnClickListener {
//            showEmptyList()
        }

        binding.error.setOnClickListener {
//            showFailedList()
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
        viewModel.foodListFragmentProps.observe(viewLifecycleOwner) { fragmentProps ->
            fragmentBinding?.let {
                it.progressPercentText.showIf(fragmentProps.userData != FailedUser)
                it.progressText.showIf(fragmentProps.userData != FailedUser)
                it.progressBar.showIf(fragmentProps.userData != FailedUser)
                it.progressBarContainer.showIf(fragmentProps.userData != FailedUser)
                it.textView.showIf(fragmentProps.userData != FailedUser)
                it.failedListMessage.showIf(fragmentProps.userData is FailedUser)
                it.failedListImage.showIf(fragmentProps.userData is FailedUser)

                it.foodGridList.showIf(fragmentProps.foodData is LoadedFoodList)
                it.foodListProgressBar.showIf(fragmentProps.foodData is LoadingFoodList)
                it.emptyListMessage.showIf(fragmentProps.foodData is EmptyFoodList)
                it.failedListImage.showIf(fragmentProps.foodData is FailedFoodList)
                it.failedListMessage.showIf(fragmentProps.foodData is FailedFoodList)

                if (fragmentProps.foodData is LoadedFoodList) {
                    (it.foodGridList?.adapter as FoodListAdapter).submitList(fragmentProps.foodData.foodList.toList())
                }

                if (fragmentProps.userData is UserProps) {
                    Log.d("TAG", "subscribeObservers: LOADED!")
                    updateUserProgress(fragmentProps.userData)
                }

                it.addFood.setOnClickListener {
                    fragmentProps.addAction(
                        FoodProps(
                            Random.nextInt(9, 2000).toString(),
                            "Teriyaki Meat Loaf with something else, and salt and sauce, just to have a really long name here",
                            "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
                            17f
                        )
                    )
                }

                it.removeFood.setOnClickListener {
                    fragmentProps.removeAction()
                }
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
            it.progressPercentText.text =
                resources.getString(R.string.user_calories_progress_percent, userProgress * 100)
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

//    private fun showEmptyList() {
//        viewModel.showEmptyList()
//    }
//
//    private fun showFailedList() {
//        viewModel.showFailedList()
//    }

    /** End of test functions block */

    private fun progressOutOfValue(value: Double): Int {
        return if (value <= 1.0) {
            ((value) * 70.0).toInt()
        } else {
            70
        }
    }
}
