package com.example.calorietracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MealsListAdapter() : ListAdapter<Meal, RecyclerView.ViewHolder>(MealItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MealViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_meal_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is MealViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    class MealItemDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mealTitle: TextView = itemView.findViewById(R.id.mealTitle)
        private val mealCalories: TextView = itemView.findViewById(R.id.mealCalories)
        private val mealImage: ImageView = itemView.findViewById(R.id.mealImage)
        private var currentMeal: Meal? = null

        fun bind(meal: Meal) {
            currentMeal = meal

            mealTitle.text = meal.mealName
            mealCalories.text = meal.mealCalories.toString()
            Glide.with(itemView.context)
                .load(meal.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(mealImage)
        }
    }
}
