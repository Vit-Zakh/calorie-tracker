package com.example.calorietracker.extensions

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.calorietracker.R
import com.example.calorietracker.data.RecyclerData

@BindingAdapter("loadImage")
fun ImageView.loadImageByUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}

@BindingAdapter("userFormattedWeight")
fun formatWeight(textView: TextView, user: RecyclerData.User) {
    textView.text = textView.resources.getString(R.string.user_weight_text, user.userWeight)
}

@BindingAdapter("formattedCalories")
fun formatCalories(textView: TextView, user: RecyclerData.User) {
    textView.text = textView.resources.getString(
        R.string.user_daily_calories_text,
        "%.${2}f".format(user.userIntake)
    )
}
