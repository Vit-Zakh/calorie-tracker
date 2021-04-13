package com.example.calorietracker.graphqltest.locations.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentLocationsListBinding
import com.example.calorietracker.utils.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : Fragment(R.layout.fragment_locations_list) {

    private val viewModel: LocationsListViewModel by activityViewModels()

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

                it.locationsData.text = "Basic Locations"
                it.locationsData.setOnClickListener { fragmentProps.navigationActionLocationsList() }

                if (fragmentProps.locationData is LocationsListProps.LoadedList) {
                    (it.responseList.adapter as LocationsListAdapter).submitList(fragmentProps.locationData.locationsList)
                }
            }
        }
    }
}
