package com.example.calorietracker.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object DataModule {

//    @Provides
//    fun provideDataSource(): DataSource {
//        return DataSource()
//    }
}
