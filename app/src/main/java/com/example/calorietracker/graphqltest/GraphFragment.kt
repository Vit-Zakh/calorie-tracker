package com.example.calorietracker.graphqltest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentGraphBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraphFragment : Fragment(R.layout.fragment_graph) {

    private val viewModel: CharacterListViewModel by activityViewModels()

    private var fragmentBinding: FragmentGraphBinding? = null
    private lateinit var charactersListAdapter: GraphListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentGraphBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.responseList.layoutManager =
                LinearLayoutManager(requireContext())
            charactersListAdapter = GraphListAdapter()
            it.responseList.adapter = charactersListAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.characterListFragmentProps.observe(viewLifecycleOwner) { fragmentProps ->
            fragmentBinding?.let {

                if (fragmentProps.characterData.isNotEmpty()) {
                    (it.responseList?.adapter as GraphListAdapter).submitList(fragmentProps.characterData)
                }
            }
        }
    }
}
