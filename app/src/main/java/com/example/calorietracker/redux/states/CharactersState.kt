package com.example.calorietracker.redux.states

import com.example.calorietracker.graphqltest.characters.FailFetchingCharacters
import com.example.calorietracker.graphqltest.characters.StartFetchingCharacters
import com.example.calorietracker.graphqltest.characters.SucceedFetchingCharacters
import com.example.calorietracker.graphqltest.characters.SucceedFetchingMoreCharacters
import com.example.calorietracker.graphqltest.characters.models.CharacterModel
import com.example.calorietracker.redux.actions.ReduxAction

data class CharactersState(
    val charactersList: List<CharacterModel> = emptyList(),
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

                charactersList = action.data,
                isLoading = false,
                isFailed = false,
                pages = action.pages,
                nextPage = action.next
            )
            is SucceedFetchingMoreCharacters -> {
                val fetchedList = action.data
                this.copy(
                    charactersList = if (charactersList.isEmpty()) fetchedList else charactersList + fetchedList,
                    nextPage = action.next
                )
            }
            else -> this
        }
    }
}
