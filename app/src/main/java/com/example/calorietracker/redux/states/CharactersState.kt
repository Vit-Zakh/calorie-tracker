package com.example.calorietracker.redux.states

import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.graphqltest.FailFetchingCharacters
import com.example.calorietracker.graphqltest.StartFetchingCharacters
import com.example.calorietracker.graphqltest.SucceedFetchingCharacters
import com.example.calorietracker.redux.actions.ReduxAction

data class CharactersState(
    val charactersList: GetCharactersQuery.Characters? = null,
    val isLoading: Boolean = false,
    val isFailed: Boolean = false
) {

    fun reduce(action: ReduxAction): CharactersState {
        return when (action) {
            is StartFetchingCharacters -> this.copy(isLoading = true)
            is FailFetchingCharacters -> this.copy(isFailed = true, isLoading = false)
            is SucceedFetchingCharacters -> this.copy(
                charactersList = action.list,
                isLoading = false,
                isFailed = false
            )
            else -> this
        }
    }
}
