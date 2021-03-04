package com.example.calorietracker.dailyintake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.databinding.*
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.DailyIntakeProps.*
import java.lang.RuntimeException

class DailyIntakeAdapter() :
    ListAdapter<DailyIntakeProps, RecyclerView.ViewHolder>(IntakeItemDiffCallback()) {

    private val VIEW_TYPE_MEAL = 1
    private val VIEW_TYPE_MEAL_LOADING = 2
    private val VIEW_TYPE_MEAL_EMPTY = 3
    private val VIEW_TYPE_MEAL_FAILED = 4
    private val VIEW_TYPE_USER = 5
    private val VIEW_TYPE_USER_LOADING = 6
    private val VIEW_TYPE_USER_FAILED = 7
    private val VIEW_TYPE_TEXT = 8

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MEAL -> {
                MealViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_meal_item, parent, false)
                )
            }
            VIEW_TYPE_MEAL_LOADING -> {
                LoadingMealViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_loading_meal_item, parent, false)
                )
            }
            VIEW_TYPE_MEAL_EMPTY -> {
                EmptyMealViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_empty_meal_item, parent, false)
                )
            }
            VIEW_TYPE_MEAL_FAILED -> {
                FailedMealViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_failed_meal_item, parent, false)
                )
            }
            VIEW_TYPE_USER -> {
                UserViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_user_item, parent, false)
                )
            }
            VIEW_TYPE_USER_LOADING -> {
                LoadingUserViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_loading_user_item, parent, false)
                )
            }
            VIEW_TYPE_USER_FAILED -> {
                FailedUserViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_failed_user_item, parent, false)
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
            is EmptyMealViewHolder -> {
                holder.bind()
            }
            is FailedMealViewHolder -> {
                holder.bind()
            }
            is UserViewHolder -> {
                holder.bind(getItem(position) as UserProps)
            }
            is FailedUserViewHolder -> {
                holder.bind()
            }
            is LoadingUserViewHolder -> {
                holder.bind()
            }
            is TextViewHolder -> {
                holder.bind()
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

        fun bind() {
            emptyMealBinding.emptyMessage = itemView.resources.getString(R.string.empty_meals_message)
        }
    }

    class LoadingMealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class FailedMealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val failedMealBinding = LayoutFailedMealItemBinding.bind(itemView)

        fun bind() {
            failedMealBinding.failedMealMessage = itemView.resources.getString(R.string.failed_meals_message)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userBinding = LayoutUserItemBinding.bind(itemView)

        fun bind(userProps: UserProps) {
            userBinding.user = userProps
        }
    }

    class LoadingUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val loadingUserBinding = LayoutLoadingUserItemBinding.bind(itemView)

        fun bind() {
            loadingUserBinding.loadingUserMessage = itemView.resources.getString(R.string.loading_user_message)
        }
    }

    class FailedUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val failedUserBinding = LayoutFailedUserItemBinding.bind(itemView)

        fun bind() {
            failedUserBinding.failedUserMessage = itemView.resources.getString(R.string.failed_user_message)
        }
    }

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textBinding = LayoutTextItemBinding.bind(itemView)

        fun bind() {
            textBinding.categoryName = itemView.resources.getString(R.string.category_text)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MealProps -> VIEW_TYPE_MEAL
            is EmptyMealsItem -> VIEW_TYPE_MEAL_EMPTY
            is LoadingMealsItem -> VIEW_TYPE_MEAL_LOADING
            is FailedMealsItem -> VIEW_TYPE_MEAL_FAILED
            is UserProps -> VIEW_TYPE_USER
            is LoadingUser -> VIEW_TYPE_USER_LOADING
            is FailedUser -> VIEW_TYPE_USER_FAILED
            is TextLine -> VIEW_TYPE_TEXT
            else -> throw RuntimeException("Crash while defining view type ${getItem(position).javaClass}")
        }
    }
}
