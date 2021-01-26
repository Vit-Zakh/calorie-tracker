package com.example.calorietracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.calorietracker.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {

    private var fragmentBinding: FragmentFoodListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodListBinding.inflate(inflater, container, false)
        fragmentBinding = binding
        return binding.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}
