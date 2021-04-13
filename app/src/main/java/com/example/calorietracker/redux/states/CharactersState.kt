package com.example.calorietracker.redux.states

import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.graphqltest.characters.FailFetchingCharacters
import com.example.calorietracker.graphqltest.characters.StartFetchingCharacters
import com.example.calorietracker.graphqltest.characters.SucceedFetchingCharacters
import com.example.calorietracker.graphqltest.characters.SucceedFetchingMoreCharacters
import com.example.calorietracker.redux.actions.ReduxAction

data class CharactersState(
    val charactersList: List<GetCharactersQuery.Result?>? = null,
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
    val pages: Int? = null,
    val nextPage: Int? = null
) {

    fun reduce(action: ReduxAction): CharactersState {
        return when (action) {
            is StartFetchingCharacters -> this.copy(isLoading = true)
            is FailFetchingCharacters -> this.copy(isFailed = true, isLoading = false)
            is SucceedFetchingCharacters -> this.copy(

                charactersList = action.data.results,
                isLoading = false,
                isFailed = false,
                pages = action.data.info?.pages,
                nextPage = action.data.info?.next
            )
            is SucceedFetchingMoreCharacters -> this.copy(
                charactersList = if (charactersList == null) action.data.results else charactersList + action.data.results!!,
                nextPage = action.data.info?.next
            )
            else -> this
        }
    }
}
