package com.example.calorietracker.foodlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.databinding.FragmentFoodListBinding
import com.example.calorietracker.models.ui.FoodListProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.utils.ListStates
import com.example.calorietracker.utils.RightSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class FoodListFragment : Fragment() {

//    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var foodListAdapter: FoodListAdapter_1
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
            showEmptyList()
        }

        binding.error.setOnClickListener {
            showError()
        }

        /** End of test buttons block */

        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.foodGridList.layoutManager =
                StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL)
            foodListAdapter = FoodListAdapter_1 { food ->
                openAddMealDialog(food)
            }
            it.foodGridList.adapter = foodListAdapter
            it.foodGridList.addItemDecoration(RightSpacingItemDecoration())
        }
    }

    private fun subscribeObservers() {
        viewModel.currentUserData.observe(viewLifecycleOwner) {
        }

        viewModel.listState.observe(viewLifecycleOwner) {
            when (it) {

                ListStates.LOADING ->
                    fragmentBinding?.foodListProgressBar?.visibility =
                        View.VISIBLE

                ListStates.SUCCESS -> {
                    fragmentBinding?.foodListProgressBar?.visibility = View.GONE
//                    viewModel.foodListData.observe(viewLifecycleOwner) { foodList ->
                    viewModel.foodListData_1.observe(viewLifecycleOwner) { foodList ->
                        refreshFoodList(foodList.toList())
                        Log.d("TAG", "subscribeObservers: отработало")
                    }
                }

                ListStates.ERROR -> {
                    fragmentBinding?.foodListProgressBar?.visibility = View.GONE
                    Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                }

                ListStates.SUCCESS_EMPTY_LIST -> {
                    fragmentBinding?.foodListProgressBar?.visibility = View.GONE
                    Toast.makeText(context, "Your list is empty", Toast.LENGTH_SHORT).show()
                }

                else -> fragmentBinding?.foodListProgressBar?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

//    private fun refreshFoodList(list: List<FoodProps>) {
//        fragmentBinding?.let {
//            (it.foodGridList.adapter as FoodListAdapter).submitList(list)
//        }
//    }
    private fun refreshFoodList(list: List<FoodListProps.FoodProps>) {
        fragmentBinding?.let {
            (it.foodGridList.adapter as FoodListAdapter_1).submitList(list)
        }
    }

//    private fun openAddMealDialog(food: FoodProps) {
    private fun openAddMealDialog(food: FoodListProps.FoodProps) {
//        findNavController().navigate(
//            FoodListFragmentDirections.actionFoodListFragmentToAddMealDialog(
//                food
//            )
//        )
    }
    private fun showError() {
        fragmentBinding?.let {
            it.foodListProgressBar?.visibility = View.GONE
            (it.foodGridList.adapter as FoodListAdapter_1).submitList(listOf(FoodListProps.ErrorFood))
        }
    }
    private fun showEmptyList() {
        fragmentBinding?.let {
            it.foodListProgressBar?.visibility = View.GONE
            (it.foodGridList.adapter as FoodListAdapter_1).submitList(listOf(FoodListProps.EmptyFood))
        }
    }
}
