package com.example.calorietracker.graphqltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.graphqltest.models.CharacterModel
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
    )

    val characterListFragmentProps: LiveData<CharacterListFragmentProps> =
        _characterListFragmentProps

    override fun onCleared() {
        store.unsubscribe(this)
    }

    override fun onUpdate(state: AppState) {
        val _characterList = when {
            state.charactersState.isLoading -> CharactersListProps.LoadingCharactersList
            state.charactersState.isFailed -> CharactersListProps.FailedCharactersList
            else -> {
                val charactersList = mutableListOf<CharacterModel>()
                state.charactersState.charactersList?.results.let {
                    it?.forEach { character ->
                        if (character != null) {
                            charactersList.add(character.mapToBusinessModel())
                        }
                    }
                }
                CharactersListProps.LoadedCharactersList(charactersList = charactersList)
            }
        }

        _characterListFragmentProps.postValue(
            _characterList?.let {
                CharacterListFragmentProps(
                    characterData = it
                )
            }
        )
    }
}
