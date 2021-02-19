package com.example.calorietracker.di

import com.example.calorietracker.dailyintake.DailyIntakeRepository
import com.example.calorietracker.dailyintake.DailyRepositoryImpl
import com.example.calorietracker.foodlist.FoodListRepository
import com.example.calorietracker.foodlist.FoodListRepositoryImpl
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.state.FoodListState
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDailyIntakeRepository(
        mealsState: MealsState,
        userState: UserState,
        apiService: TrackerApiService
    ): DailyIntakeRepository {
        return DailyRepositoryImpl(mealsState, userState, apiService)
    }

    @Singleton
    @Provides
    fun provideFoodListRepository(
        userState: UserState,
        foodListState: FoodListState,
        mealsState: MealsState,
        apiService: TrackerApiService
    ): FoodListRepository {
        return FoodListRepositoryImpl(userState, foodListState, mealsState, apiService)
    }
}
