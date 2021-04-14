package com.example.calorietracker.redux.middleware.services

import com.apollographql.apollo.api.toInput
import com.example.calorietracker.*
import com.example.calorietracker.redux.middleware.services.QueryHandler.ApolloResult

class NetworkService : QueryHandler {
    private val apolloNetworkService = ApolloNetworkService()

    override suspend fun fetchCharactersData(page: Int): ApolloResult<GetCharactersQuery.Data> =
        apolloNetworkService.query(GetCharactersQuery(page.toInput()))

    override suspend fun fetchLocationsData(): ApolloResult<GetLocationDataQuery.Data> =
        apolloNetworkService.query(GetLocationDataQuery())

    override suspend fun fetchLocationsWithTypeData(): ApolloResult<GetLocationDataWithTypeQuery.Data> =
        apolloNetworkService.query(GetLocationDataWithTypeQuery())

    override suspend fun fetchLocationsWithCreatedData(): ApolloResult<GetLocationDataWithCreatedQuery.Data> =
        apolloNetworkService.query(GetLocationDataWithCreatedQuery())

    override suspend fun fetchJointLocationsData(): ApolloResult<JointLocationsQuery.Data> =
        apolloNetworkService.query(JointLocationsQuery())
}
