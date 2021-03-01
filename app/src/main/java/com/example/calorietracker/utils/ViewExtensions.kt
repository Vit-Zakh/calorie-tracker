package com.example.calorietracker.utils

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar

fun ProgressBar.showIf(loading: Boolean) {
    this.visibility = if (loading) VISIBLE else GONE
}

fun ImageView.showIf(loading: Boolean) {
    this.visibility = if (loading) VISIBLE else GONE
}
