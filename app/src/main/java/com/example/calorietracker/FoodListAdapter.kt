package com.example.calorietracker

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

    class FoodItemDiffCallback : DiffUtil.ItemCallback<RecyclerData>() {

        override fun areItemsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
            return when {
                oldItem is Food && newItem is Food -> {
                    oldItem.id == newItem.id
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
            return when {
                oldItem is Food && newItem is Food -> {
                    oldItem == newItem
                }
                else -> false
            }
        }
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val foodBinding = LayoutFoodGridItemBinding.bind(itemView)

        fun bind(food: Food) {
            with(foodBinding) {
                foodTitle.text = food.name
                foodCalories.text = food.calories.toString()
                foodImage.loadImageByUrl(food.imageUrl)
                this.setRandomBackground()
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
    }
}

private fun LayoutFoodGridItemBinding.setRandomBackground() {

    val colorsList = listOf(
        0X0FF1E88E5.toInt(), // Blue
        0XFF7CB342.toInt(), // Green
        0XFF5E35B1.toInt(), // Violet
        0XFF8E24AA.toInt(), // Purple
        0XFFFDD835.toInt(), // Yellow
        0XFFF4511E.toInt() // Orange
    )

    val randomColor = colorsList[Random.nextInt(colorsList.size)]

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.foodImage.foreground = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                0X00000000,
                0X7CD8D8D8,
                randomColor
            )
        )
    }
    this.foodContainer.background = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(
            0X00000000,
            randomColor,
            randomColor
        )
    )
}
