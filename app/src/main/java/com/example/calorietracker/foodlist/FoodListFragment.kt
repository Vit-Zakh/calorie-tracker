package com.example.calorietracker.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.RecyclerData
import com.example.calorietracker.RecyclerData.*
import com.example.calorietracker.RightSpacingItemDecoration
import com.example.calorietracker.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {

    private lateinit var foodListAdapter: FoodListAdapter
    private var fragmentBinding: FragmentFoodListBinding? = null
    private val args: FoodListFragmentArgs by navArgs()
    private val model: FoodListViewModel by viewModels()
    private lateinit var recyclerData: MutableLiveData<List<RecyclerData.Food>>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodListBinding.inflate(inflater, container, false)
        fragmentBinding = binding
        recyclerData = model.getFoodList()

        initRecyclerView()

        recyclerData.observe(
                viewLifecycleOwner,
                Observer { foodListAdapter.submitList(it.toList()) }
        )

        val user = args.User
        setCalorieProgress(user)

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

        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.foodGridList.layoutManager =
                    StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL)
            foodListAdapter = FoodListAdapter()
            it.foodGridList.adapter = foodListAdapter
            it.foodGridList.addItemDecoration(RightSpacingItemDecoration())
//            foodListAdapter.submitList(model.getFoodList().value)
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
                it.progressPercentText.setTextColor(0XF93333)
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
