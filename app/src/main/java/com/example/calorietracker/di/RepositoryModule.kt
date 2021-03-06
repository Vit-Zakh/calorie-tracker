package com.example.calorietracker.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.calorietracker.dailyintake.DailyIntakeRepository
import com.example.calorietracker.dailyintake.DailyRepositoryImpl
import com.example.calorietracker.foodlist.FoodListRepository
import com.example.calorietracker.foodlist.FoodListRepositoryImpl
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.state.EditedUserDataSource
import com.example.calorietracker.state.FoodListDataSource
import com.example.calorietracker.state.MealsDataSource
import com.example.calorietracker.state.UserDataSource
import com.example.calorietracker.userprofile.EditedUserProfileRepository
import com.example.calorietracker.userprofile.EditedUserProfileRepositoryImpl
import com.example.calorietracker.userprofile.UserProfileRepository
import com.example.calorietracker.userprofile.UserProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        apiService: ApiService,
        sharedPreferences: SharedPreferences
    ): DailyIntakeRepository {
        return DailyRepositoryImpl(mealsDataSource, userDataSource, apiService, sharedPreferences)
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

    @Singleton
    @Provides
    fun provideUserProfileRepository(
        userDataSource: UserDataSource,
    ): UserProfileRepository {
        return UserProfileRepositoryImpl(userDataSource)
    }

    @Singleton
    @Provides
    fun provideEditedUserProfileRepository(
        editedUserDataSource: EditedUserDataSource,
        userDataSource: UserDataSource,
        sharedPreferences: SharedPreferences
    ): EditedUserProfileRepository {
        return EditedUserProfileRepositoryImpl(editedUserDataSource, userDataSource, sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("CashedUser", MODE_PRIVATE)
    }
}
