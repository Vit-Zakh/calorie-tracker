package com.example.calorietracker.redux.middleware.services

import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.GetLocationDataQuery
import com.example.calorietracker.GetLocationDataWithCreatedQuery
import com.example.calorietracker.GetLocationDataWithTypeQuery

interface QueryHandler {

    sealed class ApolloResult<T> {
        class Success<T>(val data: T) : ApolloResult<T>()
        class Error<T>(val error: com.apollographql.apollo.api.Error) : ApolloResult<T>()
    }

    suspend fun fetchCharactersData(page: Int): ApolloResult<GetCharactersQuery.Data>

    suspend fun fetchLocationsData(): ApolloResult<GetLocationDataQuery.Data>

    suspend fun fetchLocationsWithTypeData(): ApolloResult<GetLocationDataWithTypeQuery.Data>

    suspend fun fetchLocationsWithCreatedData(): ApolloResult<GetLocationDataWithCreatedQuery.Data>
}
