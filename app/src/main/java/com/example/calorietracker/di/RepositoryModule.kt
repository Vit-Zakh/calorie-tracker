package com.example.calorietracker.di

import com.example.calorietracker.dailyintake.DailyIntakeRepository
import com.example.calorietracker.dailyintake.DailyRepositoryImpl
import com.example.calorietracker.foodlist.FoodListRepository
import com.example.calorietracker.foodlist.FoodListRepositoryImpl
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.state.FoodListDataSource
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserDataSource
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
        userDataSource: UserDataSource,
        apiService: ApiService
    ): DailyIntakeRepository {
        return DailyRepositoryImpl(mealsState, userDataSource, apiService)
    }

    @Singleton
    @Provides
    fun provideFoodListRepository(
        userDataSource: UserDataSource,
        foodListDataSource: FoodListDataSource,
        mealsState: MealsState,
        apiService: ApiService
    ): FoodListRepository {
        return FoodListRepositoryImpl(userDataSource, foodListDataSource, mealsState, apiService)
    }
}
