package com.example.calorietracker.graphqltest.locations.basic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.graphqltest.locations.models.mapToUiModel
import com.example.calorietracker.modo.Screens
import com.example.calorietracker.redux.actions.ChangeScreen
import com.example.calorietracker.redux.states.AppState
import com.example.calorietracker.redux.store.AppStore
import com.example.calorietracker.redux.store.StateChangeListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsListViewModel @Inject constructor(
    private val store: AppStore
) : ViewModel(), StateChangeListener {

    private val _locationListFragmentProps: MutableLiveData<LocationListFragmentProps> =
        MutableLiveData()

    init {
        store.subscribe(this)
        store.dispatch(FetchLocationsData())
    }

    class LocationListFragmentProps(
        val locationData: LocationsListProps,
        val navigationActionLocationsList: () -> Unit
    )

    val locationListFragmentProps: LiveData<LocationListFragmentProps> =
        _locationListFragmentProps

    override fun onCleared() {
        store.unsubscribe(this)
    }

    private fun moveToLocationsList() {
        store.dispatch(ChangeScreen(Screens.LocationsWithTypeListScreen()))
    }

    override fun onUpdate(state: AppState) {
        val locationList = when {
            state.locationsState.isLoading -> LocationsListProps.LoadingList
            state.locationsState.isFailed -> LocationsListProps.FailedList
            else -> {
                LocationsListProps.LoadedList(
                    locationsList = state.locationsState.locationsList.map { it.mapToUiModel() }
                )
            }
        }

        _locationListFragmentProps.postValue(
            LocationListFragmentProps(
                locationData = locationList,
                navigationActionLocationsList = ::moveToLocationsList
            )
        )
    }
}
