package com.example.calorietracker.graphqltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.GetCharactersQuery
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
        val characterData: List<GetCharactersQuery.Result>,
    )

    val characterListFragmentProps: LiveData<CharacterListFragmentProps> =
        _characterListFragmentProps

    override fun onCleared() {
        store.unsubscribe(this)
    }

    override fun onUpdate(state: AppState) {
//        val _characterList = when {
//            state.charactersState.isLoading -> state.charactersState.charactersList
//            state.charactersState.isFailed -> state.charactersState.charactersList
//            else -> state.charactersState.charactersList
//        }
        val _characterList = mutableListOf<GetCharactersQuery.Result>()
        state.charactersState.charactersList?.results?.forEach { character ->
            character?.let { _characterList.add(it) }
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
