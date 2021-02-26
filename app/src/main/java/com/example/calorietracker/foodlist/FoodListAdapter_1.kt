package com.example.calorietracker.foodlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.databinding.*
import com.example.calorietracker.models.ui.FoodListProps
import java.lang.RuntimeException

class FoodListAdapter_1(private val clickListener: (food: FoodListProps.FoodProps) -> Unit) : ListAdapter<FoodListProps, RecyclerView.ViewHolder>(FoodItemDiffCallback_1()) {

    private val VIEW_TYPE_FOOD = 1
    private val VIEW_TYPE_FOOD_EMPTY = 2
    private val VIEW_TYPE_FOOD_ERROR = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FOOD -> {
                FoodyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_foody_grid_item, parent, false)
                )
            }
            VIEW_TYPE_FOOD_EMPTY -> {
                FoodEmptyListViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_food_empty, parent, false)
                )
            }
            VIEW_TYPE_FOOD_ERROR -> {
                FoodErrorViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_food_error, parent, false)
                )
            }
            else -> {
                throw RuntimeException("Crash while defining view holder")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FoodErrorViewHolder -> {
                holder.bind("ERROR!")
            }
            is FoodEmptyListViewHolder -> {
                holder.bind("EMPTY LIST!")
            }
            is FoodyViewHolder -> {
                holder.bind(getItem(position) as FoodListProps.FoodProps, clickListener)
            }
        }
    }

    class FoodErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val errorBinding = LayoutFoodErrorBinding.bind(itemView)

        fun bind(string: String) {
            errorBinding.errorMessage = string
        }
    }

    class FoodEmptyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val emptyBinding = LayoutFoodEmptyBinding.bind(itemView)

        fun bind(string: String) {
            emptyBinding.emptyMessage = string
        }
    }

    class FoodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val foodBinding = LayoutFoodyGridItemBinding.bind(itemView)

        fun bind(food: FoodListProps.FoodProps, clickListener: (food: FoodListProps.FoodProps) -> Unit) {
            foodBinding.food = food
            foodBinding.foodContainer.setOnClickListener { clickListener(food) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FoodListProps.FoodProps -> VIEW_TYPE_FOOD
            is FoodListProps.EmptyFood -> VIEW_TYPE_FOOD_EMPTY
            is FoodListProps.ErrorFood -> VIEW_TYPE_FOOD_ERROR
            else -> throw RuntimeException("Crash while defining view type")
        }
    }
}
