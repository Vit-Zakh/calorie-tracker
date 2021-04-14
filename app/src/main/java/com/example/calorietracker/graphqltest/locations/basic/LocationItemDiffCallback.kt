package com.example.calorietracker.graphqltest.locations.basic

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.graphqltest.locations.models.LocationModel

class LocationItemDiffCallback : DiffUtil.ItemCallback<LocationModel>() {

    override fun areItemsTheSame(oldItem: LocationModel, newItem: LocationModel): Boolean {
        return when {
            oldItem is LocationModel && newItem is LocationModel -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: LocationModel, newItem: LocationModel): Boolean {
        return when {
            oldItem is LocationModel && newItem is LocationModel -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
