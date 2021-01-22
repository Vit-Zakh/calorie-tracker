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
import de.hdodenhof.circleimageview.CircleImageView

class MealsListAdapter() : ListAdapter<Meal, RecyclerView.ViewHolder>(MealItemDiffCallback()) {

    companion object {

        const val VIEW_TYPE_MEAL = 1
        const val VIEW_TYPE_USER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return MealViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.layout_meal_item, parent, false)
//        )
        return when (viewType) {

            VIEW_TYPE_MEAL -> {
                MealViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.layout_meal_item, parent, false)
                )
            }

            VIEW_TYPE_USER -> {
                UserViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item, parent, false)
                )
            }

            else -> {
                TextViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.layout_text_item, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is MealViewHolder -> {
                holder.bind(getItem(position))
            }
            is UserViewHolder -> {
                holder.bind(DataSource.createUser())
            }
            is TextViewHolder -> {
                holder.bind("Your daily intake")
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

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userName: TextView = itemView.findViewById(R.id.userName)
        private val userImage: CircleImageView = itemView.findViewById(R.id.userImage)
        private val userWeight: TextView = itemView.findViewById(R.id.userWeight)

        fun bind(user: User) {
            userName.text = user.userName
            userWeight.text = user.userWeight.toString()
            Glide.with(itemView.context)
                .load(user.userImage)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(userImage)
        }
    }

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textToHold: TextView = itemView.findViewById(R.id.textToHold)

        fun bind(string: String) {
            textToHold.text = string
        }
    }

}
