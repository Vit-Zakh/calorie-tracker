package com.example.calorietracker

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.RecyclerData.*
import com.example.calorietracker.databinding.LayoutFoodGridItemBinding
import com.example.calorietracker.databinding.LayoutMealItemBinding
import com.example.calorietracker.databinding.LayoutTextItemBinding
import com.example.calorietracker.databinding.LayoutUserItemBinding
import com.example.calorietracker.extensions.loadImageByUrl
import java.lang.RuntimeException
import kotlin.random.Random

class MealsListAdapter() :
    ListAdapter<RecyclerData, RecyclerView.ViewHolder>(MealItemDiffCallback()) {

    private val VIEW_TYPE_MEAL = 1
    private val VIEW_TYPE_USER = 2
    private val VIEW_TYPE_TEXT = 3
    private val VIEW_TYPE_FOOD = 4

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
            VIEW_TYPE_FOOD -> {
                FoodViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_food_grid_item, parent, false)
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
                holder.bind(getItem(position) as Meal)
            }
            is UserViewHolder -> {
                holder.bind(getItem(position) as User)
            }
            is TextViewHolder -> {
                holder.bind("Your daily intake")
            }
            is FoodViewHolder -> {
                holder.bind(getItem(position) as Food)
            }
        }
    }

    class MealItemDiffCallback : DiffUtil.ItemCallback<RecyclerData>() {

        override fun areItemsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
            return when {
                oldItem is Meal && newItem is Meal -> {
                    oldItem.id == newItem.id
                }
                oldItem is User && newItem is User -> {
                    oldItem.id == newItem.id
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
            return when {
                oldItem is Meal && newItem is Meal -> {
                    oldItem == newItem
                }
                oldItem is User && newItem is User -> {
                    oldItem == newItem
                }
                else -> false
            }
        }
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mealBinding = LayoutMealItemBinding.bind(itemView)

        fun bind(meal: Meal) {
            with(mealBinding) {
                mealTitle.text = meal.mealName
                mealCalories.text = meal.getIntakeCaloriesRounded()
                mealSize.text = meal.getConvertedWeight()
                mealImage.loadImageByUrl(meal.imageUrl)
            }
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userBinding = LayoutUserItemBinding.bind(itemView)
        val res = itemView.resources

        fun bind(user: User) {
            with(userBinding) {
                userName.text = user.userName
                userWeight.text = res.getString(R.string.user_weight_text, user.userWeight)
                userDailyCalories.text =
                    res.getString(R.string.user_daily_calories_text, DataSource.getDailyCalories())
                user.userImage?.let { userImage.loadImageByUrl(it) }
            }
        }
    }

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textBinding = LayoutTextItemBinding.bind(itemView)

        fun bind(string: String) {
            with(textBinding) {
                textToHold.text = string
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
//                foodContainer.background = getRandomBackground()
                this.setRandomBackground()
            }
            with(itemView) {
                setOnClickListener {
                    Log.d("TAG", "bind: $adapterPosition ${food.name}")
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Meal -> VIEW_TYPE_MEAL
            is User -> VIEW_TYPE_USER
            is TextLine -> VIEW_TYPE_TEXT
            is Food -> VIEW_TYPE_FOOD
            else -> throw RuntimeException("Crash while defining view type")
        }
    }
}

private fun Meal.getConvertedWeight(): String {
    return if (this.mealWeight.div(1000) >= 1) {
        "${this.mealWeight.div(1000)} kg"
    } else "${this.mealWeight} g"
}

private fun Meal.getIntakeCaloriesRounded(): String {
    val intakeCalories = this.mealWeight.div(100f).times(this.mealCalories)
    return when {
        intakeCalories.div(10000) in 1f..10f -> {
            "%.${0}f".format(intakeCalories)
        }
        intakeCalories.div(10000) < 1 -> {
            "%.${2}f".format(intakeCalories)
        }
        else -> throw RuntimeException("Crash while converting calories")
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

    this.foodImage.foreground = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(
            0X00000000.toInt(),
            0X7CD8D8D8.toInt(),
            randomColor
        )
    )
    this.foodContainer.background = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(
            0X00000000.toInt(),
            randomColor,
            randomColor
        )
    )
}
