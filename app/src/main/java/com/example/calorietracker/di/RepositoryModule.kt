package com.example.calorietracker.di

import com.example.calorietracker.cache.FirstBootState
import com.example.calorietracker.cache.FoodListState
import com.example.calorietracker.cache.MealsState
import com.example.calorietracker.cache.UserState
import com.example.calorietracker.data.DataSource
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.repositories.DailyIntakeRepository
import com.example.calorietracker.repositories.DailyRepositoryImpl
import com.example.calorietracker.repositories.FoodListRepository
import com.example.calorietracker.repositories.FoodListRepositoryImpl
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
        firstBootState: FirstBootState,
        dataSource: DataSource,
        apiService: TrackerApiService
    ): DailyIntakeRepository {
        return DailyRepositoryImpl(mealsState, userState, firstBootState, dataSource, apiService)
    }

    @Singleton
    @Provides
    fun provideFoodListRepository(
        userState: UserState,
        foodListState: FoodListState,
        firstBootState: FirstBootState,
        dataSource: DataSource,
        apiService: TrackerApiService
    ): FoodListRepository {
        return FoodListRepositoryImpl(userState, foodListState, firstBootState, dataSource, apiService)
    }
}
