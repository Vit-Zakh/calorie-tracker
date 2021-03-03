package com.example.calorietracker.utils

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.calorietracker.R
import com.example.calorietracker.models.ui.DailyIntakeProps
import kotlin.random.Random

@BindingAdapter("loadImage")
fun ImageView.loadImageByUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.color.primary_800)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}

@BindingAdapter("userFormattedWeight")
fun formatWeight(textView: TextView, userProps: DailyIntakeProps.UserProps) {
    textView.text = textView.resources.getString(R.string.user_weight_text, userProps.userWeight)
}

@BindingAdapter("formattedCalories")
fun formatCalories(textView: TextView, userProps: DailyIntakeProps.UserProps) {
    textView.text = textView.resources.getString(
        R.string.user_daily_calories_text,
        "%.${2}f".format(userProps.userIntake)
    )
}

@BindingAdapter("randomBackground")
fun randomBackground(view: View, random: Boolean) {
    val colorsList = listOf(
        0X0FF1E88E5.toInt(), // Blue
        0XFF7CB342.toInt(), // Green
        0XFF5E35B1.toInt(), // Violet
        0XFF8E24AA.toInt(), // Purple
        0XFFFDD835.toInt(), // Yellow
        0XFFF4511E.toInt() // Orange
    )

    val randomColor = colorsList[Random.nextInt(colorsList.size)]

    if (random)
        view.background = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                0X00000000,
                randomColor,
                randomColor
            )
        )
}
