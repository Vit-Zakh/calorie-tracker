package com.example.calorietracker.graphqltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.graphqltest.models.CharactersListProps
import com.example.calorietracker.graphqltest.models.mapToBusinessModel
import com.example.calorietracker.redux.states.AppState
import com.example.calorietracker.redux.store.AppStore
import com.example.calorietracker.redux.store.StateChangeListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val store: AppStore
) : ViewModel(), StateChangeListener {

    private val _characterListFragmentProps: MutableLiveData<CharacterListFragmentProps> =
        MutableLiveData()

    init {
        store.subscribe(this)
        store.dispatch(FetchCharactersData())
    }

    class CharacterListFragmentProps(
        val characterData: CharactersListProps,
        val loadNextPage: () -> Unit,
    )

    val characterListFragmentProps: LiveData<CharacterListFragmentProps> =
        _characterListFragmentProps

    override fun onCleared() {
        store.unsubscribe(this)
    }

    private fun loadNextPage() {
        store.appState.charactersState.nextPage?.let { store.dispatch(FetchMoreCharacters(it)) }
    }

    override fun onUpdate(state: AppState) {
        val characterList = when {
            state.charactersState.isLoading -> CharactersListProps.LoadingCharactersList
            state.charactersState.isFailed -> CharactersListProps.FailedCharactersList
            else -> {
                CharactersListProps.LoadedCharactersList(
                    charactersList = state.charactersState.charactersList?.map { it?.mapToBusinessModel() }
                )
            }
        }

        _characterListFragmentProps.postValue(
            CharacterListFragmentProps(
                characterData = characterList,
                loadNextPage = ::loadNextPage
            )
        )
    }
}
