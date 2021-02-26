package com.example.calorietracker.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.databinding.FragmentFoodListBinding
import com.example.calorietracker.models.ui.FoodListProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.utils.RightSpacingItemDecoration
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
        binding.viewModel = viewModel
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
        viewModel.currentUserData.observe(viewLifecycleOwner) {
        }

        viewModel.foodListData.observe(viewLifecycleOwner) { listData ->
            when (listData) {
                is FoodListProps.LoadedFoodList -> refreshFoodList(listData.foodList)
                is FoodListProps.LoadingFoodList -> showProgressBar()
                is FoodListProps.EmptyFoodList -> showEmptyListMessage()
                is FoodListProps.FailedFoodList -> showFailedListMessage()
            }
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun refreshFoodList(list: List<FoodProps>) {
        fragmentBinding?.let {
            (it.foodGridList.adapter as FoodListAdapter).submitList(list)
            it.foodGridList.visibility = VISIBLE
            it.foodListProgressBar.visibility = GONE
            it.emptyListMessage.visibility = GONE
            it.failedListMessage.visibility = GONE
            it.textView.text = "Choose your meal"
        }
    }

    private fun openAddMealDialog(food: FoodProps) {
        findNavController().navigate(
            FoodListFragmentDirections.actionFoodListFragmentToAddMealDialog(
                food
            )
        )
    }

    private fun showEmptyListMessage() {
        fragmentBinding?.let {
            it.foodListProgressBar.visibility = GONE
            it.emptyListMessage.visibility = VISIBLE
            it.failedListMessage.visibility = GONE
            it.foodGridList.visibility = GONE
            it.textView.text = "Your food list is empty"
        }
    }

    private fun showFailedListMessage() {
        fragmentBinding?.let {
            it.foodListProgressBar.visibility = GONE
            it.emptyListMessage.visibility = GONE
            it.failedListMessage.visibility = VISIBLE
            it.foodGridList.visibility = GONE
            it.textView.text = "Whoops!"
        }
    }

    private fun showProgressBar() {
        fragmentBinding?.let {
            it.foodListProgressBar.visibility = VISIBLE
            it.emptyListMessage.visibility = GONE
            it.failedListMessage.visibility = GONE
            it.textView.text = "Choose your meal"
        }
    }
}
