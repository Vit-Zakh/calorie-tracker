package com.example.calorietracker.redux.middleware.services

import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.JointLocationsQuery

interface QueryHandler {

    sealed class ApolloResult<T> {
        class Success<T>(val data: T) : ApolloResult<T>()
        class Error<T>(val error: com.apollographql.apollo.api.Error) : ApolloResult<T>()
    }

    suspend fun fetchCharactersData(page: Int): ApolloResult<GetCharactersQuery.Data>

    suspend fun fetchJointLocationsData(): ApolloResult<JointLocationsQuery.Data>
}
