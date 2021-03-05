package com.example.calorietracker.models.ui

sealed class FoodListProps {

    class LoadedFoodList(val foodList: List<FoodProps>) : FoodListProps()
    object LoadingFoodList : FoodListProps()
    object EmptyFoodList : FoodListProps()
    object FailedFoodList : FoodListProps()
}
