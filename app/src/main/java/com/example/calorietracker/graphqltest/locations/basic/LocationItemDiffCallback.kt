package com.example.calorietracker.graphqltest.locations.basic

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.graphqltest.locations.models.LocationProps

class LocationItemDiffCallback : DiffUtil.ItemCallback<LocationProps>() {

    override fun areItemsTheSame(oldItem: LocationProps, newItem: LocationProps): Boolean {
        return when {
            oldItem is LocationProps && newItem is LocationProps -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: LocationProps, newItem: LocationProps): Boolean {
        return when {
            oldItem is LocationProps && newItem is LocationProps -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
