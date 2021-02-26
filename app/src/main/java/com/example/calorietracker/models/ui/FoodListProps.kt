package com.example.calorietracker.models.ui

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

sealed class FoodListProps {

    class LoadedFoodList(val foodList: List<FoodProps>) : FoodListProps()
    object LoadingFoodList : FoodListProps()
    object EmptyFoodList : FoodListProps()
    object FailedFoodList : FoodListProps()
}
