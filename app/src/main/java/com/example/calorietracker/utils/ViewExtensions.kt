package com.example.calorietracker.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.showIf(isVisible: Boolean) {
    this.visibility = if (isVisible) VISIBLE else GONE
}

fun View.click(action: (() -> Unit)?) {
    this.setOnClickListener { action?.invoke() }
}
