package com.example.calorietracker.redux.middleware.services

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.example.calorietracker.redux.middleware.services.QueryHandler.ApolloResult

class ApolloNetworkService() {

    private val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()

    suspend fun <D : Operation.Data, T, V : Operation.Variables> query(query: Query<D, T, V>): ApolloResult<T> {
        return processResponse(apolloClient.query(query).toDeferred().await())
    }

    private fun <T> processResponse(response: Response<T>): ApolloResult<T> {
        val data: T? = response.data
        val errors = response.errors
        return when {
            data != null && errors.isNullOrEmpty() -> ApolloResult.Success(data)
            !errors.isNullOrEmpty() -> ApolloResult.Error(errors.first())
            else -> ApolloResult.Error(Error("Whoops! Unknown error!"))
        }
    }
}
