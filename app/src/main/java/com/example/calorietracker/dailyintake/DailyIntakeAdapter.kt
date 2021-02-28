package com.example.calorietracker.dailyintake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.databinding.LayoutEmptyMealItemBinding
import com.example.calorietracker.databinding.LayoutMealItemBinding
import com.example.calorietracker.databinding.LayoutTextItemBinding
import com.example.calorietracker.databinding.LayoutUserItemBinding
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.DailyIntakeProps.*
import java.lang.RuntimeException

class DailyIntakeAdapter() :
    ListAdapter<DailyIntakeProps, RecyclerView.ViewHolder>(IntakeItemDiffCallback()) {

    private val VIEW_TYPE_MEAL = 1
    private val VIEW_TYPE_MEAL_EMPTY = 2
    private val VIEW_TYPE_MEAL_FAILED = 3
    private val VIEW_TYPE_USER = 4
    private val VIEW_TYPE_USER_FAILED = 5
    private val VIEW_TYPE_TEXT = 6

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MEAL -> {
                MealViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_meal_item, parent, false)
                )
            }
            VIEW_TYPE_USER -> {
                UserViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_user_item, parent, false)
                )
            }
            VIEW_TYPE_TEXT -> {
                TextViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_text_item, parent, false)
                )
            }
            else -> {
                throw RuntimeException("Crash while defining view holder")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MealViewHolder -> {
                holder.bind(getItem(position) as MealProps)
            }
            is UserViewHolder -> {
                holder.bind(getItem(position) as UserProps)
            }
            is TextViewHolder -> {
                holder.bind("Your daily intake")
            }
        }
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mealBinding = LayoutMealItemBinding.bind(itemView)

        fun bind(mealProps: MealProps) {
            mealBinding.meal = mealProps
        }
    }

    class EmptyMealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val emptyMealBinding = LayoutEmptyMealItemBinding.bind(itemView)

        fun bind(emptyMessage: String) {
            emptyMealBinding.emptyMessage = emptyMessage
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userBinding = LayoutUserItemBinding.bind(itemView)

        fun bind(userProps: UserProps) {
            userBinding.user = userProps
        }
    }

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textBinding = LayoutTextItemBinding.bind(itemView)

        fun bind(string: String) {
            textBinding.categoryName = string
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MealProps -> VIEW_TYPE_MEAL
            is UserProps -> VIEW_TYPE_USER
            is TextLine -> VIEW_TYPE_TEXT
            else -> throw RuntimeException("Crash while defining view type")
        }
    }
}
