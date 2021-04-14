package com.example.calorietracker.graphqltest.locations.with_created

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.graphqltest.locations.basic.LocationsListProps
import com.example.calorietracker.graphqltest.locations.models.mapToUiModel
import com.example.calorietracker.modo.Screens
import com.example.calorietracker.redux.actions.ChangeScreen
import com.example.calorietracker.redux.states.AppState
import com.example.calorietracker.redux.store.AppStore
import com.example.calorietracker.redux.store.StateChangeListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsWithCreatedListViewModel @Inject constructor(
    private val store: AppStore
) : ViewModel(), StateChangeListener {

    private val _locationListFragmentProps: MutableLiveData<LocationListFragmentProps> =
        MutableLiveData()

    init {
        store.subscribe(this)
        store.dispatch(FetchLocationsDataWithCreated())
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
        store.dispatch(ChangeScreen(Screens.LocationsListScreen()))
    }

    override fun onUpdate(state: AppState) {
        val locationList = when {
            else -> {
                LocationsListProps.LoadedList(
                    locationsList = state.locationsWithCreatedStateState.locationsList.map { it.mapToUiModel() }
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
