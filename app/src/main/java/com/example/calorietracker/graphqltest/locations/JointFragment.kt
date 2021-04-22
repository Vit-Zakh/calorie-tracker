package com.example.calorietracker.graphqltest.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentLocationsListBinding
import com.example.calorietracker.graphqltest.locations.basic.LocationsListAdapter
import com.example.calorietracker.graphqltest.locations.basic.LocationsListProps
import com.example.calorietracker.redux.store.AppStore
import com.example.calorietracker.utils.click
import com.example.calorietracker.utils.showIf
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JointFragment() : Fragment(R.layout.fragment_locations_list) {

    @Inject
    lateinit var store: AppStore

    @Inject
    lateinit var viewModelAssistedFactory: CustomViewModelProvider

    private val viewModel: JointLocationsViewModel by viewModels() {
        CustomViewModelFactory.provideFactory(
            viewModelAssistedFactory,
            ViewModelParams(arguments?.getString("id").toString())
        )
    }

    private var fragmentBinding: FragmentLocationsListBinding? = null
    private lateinit var locationsListAdapter: LocationsListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLocationsListBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.responseList.layoutManager =
                LinearLayoutManager(requireContext())
            locationsListAdapter = LocationsListAdapter()
            it.responseList.adapter = locationsListAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.locationListFragmentProps.observe(viewLifecycleOwner) { fragmentProps ->
            fragmentBinding?.let {
                it.locationsProgressBar.showIf(fragmentProps.locationData is LocationsListProps.LoadingList)
                it.failedListImage.showIf(fragmentProps.locationData is LocationsListProps.FailedList)
                it.locationsData.text = arguments?.getString("id").toString()
                it.locationsData.click { fragmentProps.navigationActionLocationsList() }
                if (fragmentProps.locationData is LocationsListProps.LoadedList) {
                    (it.responseList.adapter as LocationsListAdapter).submitList(
                        fragmentProps.locationData.locationsList
                    )
                }
            }
        }
    }
}
