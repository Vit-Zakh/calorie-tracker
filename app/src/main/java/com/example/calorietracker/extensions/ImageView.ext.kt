package com.example.calorietracker.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.calorietracker.R

fun ImageView.loadImageByUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}
