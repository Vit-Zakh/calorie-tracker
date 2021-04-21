package com.example.calorietracker.graphqltest.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.graphqltest.locations.basic.FetchLocationsData
import com.example.calorietracker.graphqltest.locations.basic.LocationsListProps
import com.example.calorietracker.graphqltest.locations.models.mapToUiModel
import com.example.calorietracker.graphqltest.locations.with_created.FetchLocationsDataWithCreated
import com.example.calorietracker.graphqltest.locations.with_type.FetchLocationsDataWithType
import com.example.calorietracker.modo.Screens
import com.example.calorietracker.redux.actions.ChangeScreen
import com.example.calorietracker.redux.states.AppState
import com.example.calorietracker.redux.store.AppStore
import com.example.calorietracker.redux.store.StateChangeListener

class JointLocationsViewModel(private val store: AppStore, val id: String) :
    ViewModel(), StateChangeListener {

    private val _locationListFragmentProps: MutableLiveData<LocationListFragmentProps> =
        MutableLiveData()

    init {
        store.subscribe(this)
        when (id) {
            "Raw Locations" -> {
                store.dispatch(FetchLocationsData())
            }
            "Locations With Created" -> {
                store.dispatch(FetchLocationsDataWithCreated())
            }
            "Locations With Type" -> {
                store.dispatch(FetchLocationsDataWithType())
            }
            else -> throw RuntimeException("Crash while fetching data")
        }
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

    private fun navigationAction() = when (id) {
        "Raw Locations" -> {
            store.dispatch(ChangeScreen(Screens.LocationsWithTypeListScreen()))
        }
        "Locations With Created" -> {
            store.dispatch(ChangeScreen(Screens.LocationsListScreen()))
        }
        "Locations With Type" -> {
            store.dispatch(ChangeScreen(Screens.LocationsWithCreatedListScreen()))
        }
        else -> throw RuntimeException("Crash while navigating to locations fragment")
    }

    override fun onUpdate(state: AppState) {

        val locationList = when (id) {
            "Raw Locations" -> {
                if (state.locationsState.isLoading) LocationsListProps.LoadingList
                if (state.locationsState.isFailed) LocationsListProps.FailedList
                else LocationsListProps.LoadedList(
                    locationsList = state.locationsState.locationsList.map { it.mapToUiModel() }
                )
            }
            "Locations With Created" -> {
                if (state.locationsWithCreatedStateState.isLoading) LocationsListProps.LoadingList
                if (state.locationsWithCreatedStateState.isFailed) LocationsListProps.FailedList
                else LocationsListProps.LoadedList(
                    locationsList = state.locationsWithCreatedStateState.locationsList.map { it.mapToUiModel() }
                )
            }
            "Locations With Type" -> {
                if (state.locationsWithTypeState.isLoading) LocationsListProps.LoadingList
                if (state.locationsWithTypeState.isFailed) LocationsListProps.FailedList
                else LocationsListProps.LoadedList(
                    locationsList = state.locationsWithTypeState.locationsList.map { it.mapToUiModel() }
                )
            }
            else -> LocationsListProps.FailedList
        }

        _locationListFragmentProps.postValue(
            LocationListFragmentProps(
                locationData = locationList,
                navigationActionLocationsList = ::navigationAction
            )
        )
    }
}
