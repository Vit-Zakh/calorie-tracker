package com.example.calorietracker.foodlist

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.RecyclerData
import com.example.calorietracker.RecyclerData.*
import com.example.calorietracker.databinding.LayoutFoodGridItemBinding
import com.example.calorietracker.extensions.loadImageByUrl
import java.lang.RuntimeException
import kotlin.random.Random

class FoodListAdapter() :
    ListAdapter<RecyclerData, FoodListAdapter.FoodViewHolder>(FoodItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return try {
            FoodViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_food_grid_item, parent, false)
            )
        } catch (e: Exception) {
            throw RuntimeException("Crash while defining view holder")
        }
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position) as Food)
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val foodBinding = LayoutFoodGridItemBinding.bind(itemView)

        fun bind(food: Food) {
            with(foodBinding) {
                foodTitle.text = food.name
                foodCalories.text = food.calories.toString()
                foodImage.loadImageByUrl(food.imageUrl)
                gradientFilter.background = getRandomBackground()
            }
            with(itemView) {
                setOnClickListener {
                    findNavController().navigate(
                        FoodListFragmentDirections.actionFoodListFragmentToAddMealDialog(
                            food
                        )
                    )
                }
            }
        }

        private fun getRandomBackground(): GradientDrawable {
            val colorsList = listOf(
                0X0FF1E88E5.toInt(), // Blue
                0XFF7CB342.toInt(), // Green
                0XFF5E35B1.toInt(), // Violet
                0XFF8E24AA.toInt(), // Purple
                0XFFFDD835.toInt(), // Yellow
                0XFFF4511E.toInt() // Orange
            )

            val randomColor = colorsList[Random.nextInt(colorsList.size)]

            return GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(
                    0X00000000,
                    randomColor,
                    randomColor
                )
            )
        }
    }
}
