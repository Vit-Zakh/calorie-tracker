package com.example.calorietracker.graphqltest.locations.basic

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.databinding.LayoutLocationItemBinding
import com.example.calorietracker.graphqltest.locations.models.LocationProps

class LocationsListAdapter() :
    ListAdapter<LocationProps, LocationsListAdapter.LocationViewHolder>(
        LocationItemDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationViewHolder {
        return try {
            LocationViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_location_item, parent, false)
            )
        } catch (e: Exception) {
            throw RuntimeException("Crash while defining view holder")
        }
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position) as LocationProps)
    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val locationBinding = LayoutLocationItemBinding.bind(itemView)

        fun bind(location: LocationProps) {
            location.let {
                locationBinding.textId.text = it.id
                locationBinding.textName.text = it.name
                locationBinding.textDimension.text = it.dimension
                if (it.created != null) locationBinding.textCreated.text = it.created else locationBinding.textCreated.visibility = GONE
                if (it.type != null) locationBinding.textType.text = it.type else locationBinding.textType.visibility = GONE
            }
        }
    }
}
