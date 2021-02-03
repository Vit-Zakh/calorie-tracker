package com.example.calorietracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.RecyclerData.*
import com.example.calorietracker.databinding.LayoutMealItemBinding
import com.example.calorietracker.databinding.LayoutTextItemBinding
import com.example.calorietracker.databinding.LayoutUserItemBinding
import com.example.calorietracker.extensions.loadImageByUrl
import java.lang.RuntimeException

class MealsListAdapter() :
    ListAdapter<RecyclerData, RecyclerView.ViewHolder>(MealItemDiffCallback()) {

    private val VIEW_TYPE_MEAL = 1
    private val VIEW_TYPE_USER = 2
    private val VIEW_TYPE_TEXT = 3

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
                holder.bind(getItem(position) as Meal)
            }
            is UserViewHolder -> {
                holder.bind(getItem(position) as User)
            }
            is TextViewHolder -> {
                holder.bind("Your daily intake")
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            (holder as UserViewHolder).bindWithPayloads((getItem(position) as User), (payloads[0] as Bundle))
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
                    res.getString(R.string.user_daily_calories_text, "%.${2}f".format(user.userIntake))
                userImage.loadImageByUrl(user.userImage)
            }
        }

        fun bindWithPayloads(user: User, bundle: Bundle) {

            with(userBinding) {
                userName.text = user.userName
                userWeight.text = res.getString(R.string.user_weight_text, user.userWeight)
                userDailyCalories.text =
                    res.getString(R.string.user_daily_calories_text, "%.${2}f".format(bundle.getDouble("dailyIntake")))
                userImage.loadImageByUrl(user.userImage)
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

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Meal -> VIEW_TYPE_MEAL
            is User -> VIEW_TYPE_USER
            is TextLine -> VIEW_TYPE_TEXT
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
