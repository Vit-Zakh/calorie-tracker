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
import com.example.calorietracker.RecyclerData.*
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
                        .inflate(R.layout.layout_meal_item_2, parent, false)
                )
            }
            VIEW_TYPE_USER -> {
                UserViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_user_item, parent, false)
                )
            }
            else -> {
                TextViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_text_item, parent, false)
                )
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

        private val mealTitle: TextView = itemView.findViewById(R.id.mealTitle)
        private val mealCalories: TextView = itemView.findViewById(R.id.mealCalories)
        private val mealImage: ImageView = itemView.findViewById(R.id.mealImage)
        private val mealSize: TextView = itemView.findViewById(R.id.mealSize)

        fun bind(meal: Meal) {

            mealTitle.text = meal.mealName
            mealCalories.text = meal.getIntakeCaloriesRounded()
            mealSize.text = meal.getConvertedWeight()
            mealImage.loadImageByUrl(meal.imageUrl)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userName: TextView = itemView.findViewById(R.id.userName)
        private val userImage: ImageView = itemView.findViewById(R.id.userImage)
        private val userWeight: TextView = itemView.findViewById(R.id.userWeight)
        private val userDailyCalories: TextView = itemView.findViewById(R.id.userDailyCalories)

        fun bind(user: User) {
            userName.text = user.userName
            userWeight.text = "${user.userWeight} kg"
            userDailyCalories.text = "${DataSource.getDailyCalories()} kcal"
            userImage.loadCircleImageByUrl(user.userImage)
        }
    }

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textToHold: TextView = itemView.findViewById(R.id.textToHold)

        fun bind(string: String) {
            textToHold.text = string
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

private fun ImageView.loadImageByUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}

private fun ImageView.loadCircleImageByUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}
