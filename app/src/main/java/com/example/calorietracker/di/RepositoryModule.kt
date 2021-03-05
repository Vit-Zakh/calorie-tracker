package com.example.calorietracker.di

import com.example.calorietracker.dailyintake.DailyIntakeRepository
import com.example.calorietracker.dailyintake.DailyRepositoryImpl
import com.example.calorietracker.foodlist.FoodListRepository
import com.example.calorietracker.foodlist.FoodListRepositoryImpl
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.state.FoodListDataSource
import com.example.calorietracker.state.MealsDataSource
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
        mealsDataSource: MealsDataSource,
        userDataSource: UserDataSource,
        apiService: ApiService
    ): DailyIntakeRepository {
        return DailyRepositoryImpl(mealsDataSource, userDataSource, apiService)
    }

    @Singleton
    @Provides
    fun provideFoodListRepository(
        userDataSource: UserDataSource,
        foodListDataSource: FoodListDataSource,
        mealsDataSource: MealsDataSource,
        apiService: ApiService
    ): FoodListRepository {
        return FoodListRepositoryImpl(userDataSource, foodListDataSource, mealsDataSource, apiService)
    }
}
