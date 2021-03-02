package com.example.calorietracker.utils

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

fun ProgressBar.showIf(loading: Boolean) {
    this.visibility = if (loading) VISIBLE else GONE
}

fun ImageView.showIf(loading: Boolean) {
    this.visibility = if (loading) VISIBLE else GONE
}

fun TextView.showIf(loading: Boolean) {
    this.visibility = if (loading) VISIBLE else GONE
}
