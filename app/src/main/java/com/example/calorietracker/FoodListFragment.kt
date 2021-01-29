package com.example.calorietracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.RecyclerData.*
import com.example.calorietracker.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {

    private lateinit var foodListAdapter: FoodListAdapter
    private var fragmentBinding: FragmentFoodListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodListBinding.inflate(inflater, container, false)
        fragmentBinding = binding
        initRecyclerView()

        val user = arguments?.getParcelable<User>("User")
        user?.let { setCalorieProgress(it) }
        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.foodGridList.layoutManager =
                StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL)
            foodListAdapter = FoodListAdapter()
            it.foodGridList.adapter = foodListAdapter
            it.foodGridList.addItemDecoration(RightSpacingItemDecoration())
            foodListAdapter.submitList(DataSource.foodList)
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
