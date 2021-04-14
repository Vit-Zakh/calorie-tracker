package com.example.calorietracker.redux.middleware.services

import com.apollographql.apollo.api.toInput
import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.JointLocationsQuery
import com.example.calorietracker.redux.middleware.services.QueryHandler.ApolloResult

class NetworkService : QueryHandler {
    private val apolloNetworkService = ApolloNetworkService()

    override suspend fun fetchCharactersData(page: Int): ApolloResult<GetCharactersQuery.Data> =
        apolloNetworkService.query(GetCharactersQuery(page.toInput()))

    override suspend fun fetchJointLocationsData(): ApolloResult<JointLocationsQuery.Data> =
        apolloNetworkService.query(JointLocationsQuery())
}
