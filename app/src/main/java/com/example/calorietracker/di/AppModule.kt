package com.example.calorietracker.di

import com.example.calorietracker.redux.states.AppState
import com.example.calorietracker.redux.store.AppStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppStore(): AppStore {
        return AppStore(AppState())
    }
}
