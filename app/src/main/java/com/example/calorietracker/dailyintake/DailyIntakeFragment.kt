package com.example.calorietracker.dailyintake

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentDailyIntakeBinding
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_daily_intake.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class DailyIntakeFragment : Fragment(R.layout.fragment_daily_intake) {

    private var fragmentBinding: FragmentDailyIntakeBinding? = null
    private val viewModel: DailyIntakeViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDailyIntakeBinding.inflate(inflater, container, false)
        fragmentBinding = binding
        initRecyclerView()

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.intakeNavigationView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.bottomAppBar.setNavigationOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.intakeNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.toProfile -> findNavController().navigate(
                    DailyIntakeFragmentDirections.actionDailyIntakeFragmentToUserProfileFragment()
                )
                R.id.toDailtyIntake -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            true
        }

//        binding.floatingActionButton.setOnClickListener {
//            openFoodList()
//        }
        binding.apiTest.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val apolloClient = ApolloClient.builder()
//                    .serverUrl("https://your.domain/graphql/endpoint")
//                    .build()
//
// // in your coroutine scope, call `ApolloClient.query(...).toDeferred().await()`
            CoroutineScope(Dispatchers.IO).launch {
                val response = try {
                    apolloClient.query(GetCharactersQuery()).toDeferred().await()
                } catch (e: ApolloException) {
                    // handle protocol errors
                    return@launch
                }

                val launch = response.data?.characters
                if (launch == null || response.hasErrors()) {
                    // handle application errors
                    return@launch
                }

                // launch now contains a typesafe model of your data
                Log.d("qlTAG:", "onCreateView: ${launch.results}")
            }
        }

        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.dailyIntakeList.layoutManager = LinearLayoutManager(requireContext())
            it.dailyIntakeList.adapter = DailyIntakeAdapter()
        }
    }

    private fun subscribeObservers() {
        viewModel.dailyFragmentProps.observe(viewLifecycleOwner) { fragmentProps ->
            refreshIntakeList(fragmentProps.list)

            fragmentBinding?.let {
                addMeal.setOnClickListener {
                    fragmentProps.addAction(
                        DailyIntakeProps.MealProps(
                            Random.nextInt(20, 1998).toString(),
                            "Popcorn",
                            "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
                            490f,
                            213f
                        )
                    )
                }
                removeMeal.setOnClickListener {
                    fragmentProps.removeAction()
                }
                floatingActionButton.setOnClickListener {
                    fragmentProps.navigationAction()
                }
            }
        }
    }

    private fun refreshIntakeList(list: List<DailyIntakeProps>) {

        fragmentBinding?.let {
            (it.dailyIntakeList.adapter as DailyIntakeAdapter).submitList(list)
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun openFoodList() {
//        findNavController().navigate(
//            DailyIntakeFragmentDirections.actionDailyIntakeFragmentToFoodListFragment()ылн
//        )
//        MyApplication.modo.replace(Screens.FoodListScreen())
    }

    val apolloClient = ApolloClient.builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()
}
