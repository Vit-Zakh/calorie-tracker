package com.example.calorietracker.redux.middleware

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.graphqltest.FetchCharactersData
import com.example.calorietracker.graphqltest.SucceedFetchingCharacters
import com.example.calorietracker.models.network.*
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.network.NetworkResponse
import com.example.calorietracker.network.NetworkResponseAdapterFactory
import com.example.calorietracker.redux.actions.*
import com.example.calorietracker.redux.store.AppStore
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkMiddleware(val store: AppStore) : ReduxMiddleware {

    private val apiService = Retrofit.Builder()
        .baseUrl("https://calories-tracker.free.beeceptor.com/")
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(ApiService::class.java)

    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()

    override fun apply(action: ReduxAction) {
        when (action) {
            is FetchInitialData -> {
                CoroutineScope(Dispatchers.IO).launch {
//                    refreshMealsList(apiService.getMeals())
                    delay(2000)
                    store.dispatch(
                        SucceedFetchingMeals(
                            meals = listOf(
                                MealResponse(
                                    calories = "700",
                                    date = "22/10",
                                    id = "1",
                                    name = "Ginger Pork",
                                    url = "https://images.unsplash.com/photo-1601748361140-03605c34e843?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                                    weight = "600"
                                ),
                                MealResponse(
                                    calories = "700",
                                    date = "22/10",
                                    id = "1",
                                    name = "Ginger Pork",
                                    url = "https://images.unsplash.com/photo-1601748361140-03605c34e843?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                                    weight = "600"
                                ),
                                MealResponse(
                                    calories = "700",
                                    date = "22/10",
                                    id = "1",
                                    name = "Ginger Pork",
                                    url = "https://images.unsplash.com/photo-1601748361140-03605c34e843?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                                    weight = "600"
                                ),
                                MealResponse(
                                    calories = "700",
                                    date = "22/10",
                                    id = "1",
                                    name = "Ginger Pork",
                                    url = "https://images.unsplash.com/photo-1601748361140-03605c34e843?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                                    weight = "600"
                                ),
                            )
                        )
                    )
                }

                CoroutineScope(Dispatchers.IO).launch {
//                    refreshUser(apiService.getUser())
                    delay(1000)
                    store.dispatch(
                        SucceedFetchingUser(
                            user = UserResponse(
                                id = "0",
                                image = "https://cataas.com/cat/cute",
                                name = "Кошка Машка",
                                currentIntake = 1500.0,
                                maxIntake = 20000f,
                                weight = 5f,
                                age = 2,
                                backgroundImage = "https://images.unsplash.com/photo-1611068120738-e3801fcaa00a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"
                            )
                        )
                    )
                }

                CoroutineScope(Dispatchers.IO).launch {
//                    refreshFoodList(apiService.getFoodList())
                    delay(3000)
                    store.dispatch(
                        SucceedFetchingFood(
                            foodList = listOf(
                                FoodResponse(
                                    calories = "700",
                                    id = "a",
                                    name = "Pasta",
                                    url = "https://images.unsplash.com/photo-1611068120738-e3801fcaa00a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"
                                ),
                                FoodResponse(
                                    calories = "700",
                                    id = "b",
                                    name = "Broccoli",
                                    url = "https://images.unsplash.com/photo-1611095758464-6ff38fe3ca65?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"
                                ),
                                FoodResponse(
                                    calories = "700",
                                    id = "c",
                                    name = "Stew",
                                    url = "https://images.unsplash.com/photo-1528344476859-60b256f33f6f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=680&q=80"
                                ),
                                FoodResponse(
                                    calories = "700",
                                    id = "z",
                                    name = "Pizza",
                                    url = "https://images.unsplash.com/photo-1601748361140-03605c34e843?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"
                                ),
                            )
                        )
                    )
                }
            }
            is FetchCharactersData -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = try {
                        apolloClient.query(GetCharactersQuery()).toDeferred().await()
                    } catch (e: ApolloException) {
                        // handle protocol errors
                        return@launch
                    }

                    val launch = response.data?.characters
                    if (launch == null || response.hasErrors()) {
                        // handle application errors
                        return@launch
                    }
                    store.dispatch(SucceedFetchingCharacters(launch))
                }
            }
        }
    }

    private fun refreshUser(user: NetworkResponse<UserResponse, Error>) {
        when (user) {
            is NetworkResponse.Success -> {
                store.dispatch(SucceedFetchingUser(user = user.body))
            }
            else -> {
                store.dispatch(
                    FailFetchingUser(error = Error("Cannot fetch user data"))
                )
            }
        }
    }

    private fun refreshMealsList(meals: NetworkResponse<MealsListResponse, java.lang.Error>) {
        when (meals) {
            is NetworkResponse.Success ->
                store.dispatch(SucceedFetchingMeals(meals = meals.body.meals))
            else -> {
                store.dispatch(FailFetchingMeals(error = Error("Cannot fetch meals data")))
            }
        }
    }

    private fun refreshFoodList(foods: NetworkResponse<FoodListResponse, java.lang.Error>) {
        when (foods) {
            is NetworkResponse.Success ->
                store.dispatch(SucceedFetchingFood(foodList = foods.body.food))
            else -> {
                store.dispatch(FailFetchingFood(error = Error("Cannot fetch food data")))
            }
        }
    }
}
