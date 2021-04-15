package com.example.calorietracker.graphqltest.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentCharactersListBinding
import com.example.calorietracker.graphqltest.EndlessScroller
import com.example.calorietracker.utils.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters_list) {

    private val viewModel: CharacterListViewModel by activityViewModels()

    private var fragmentBinding: FragmentCharactersListBinding? = null
    private lateinit var charactersListAdapter: CharactersListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        fragmentBinding?.let {
            it.responseList.layoutManager =
                LinearLayoutManager(requireContext())
            charactersListAdapter = CharactersListAdapter()
            it.responseList.addOnScrollListener(
                EndlessScroller() {
                    viewModel.characterListFragmentProps.value?.loadNextPage?.invoke()
                }
            )
            it.responseList.adapter = charactersListAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.characterListFragmentProps.observe(viewLifecycleOwner) { fragmentProps ->
            fragmentBinding?.let {
                it.charactersProgressBar.showIf(fragmentProps.characterData is CharactersListProps.LoadingCharactersList)
                it.failedListImage.showIf(fragmentProps.characterData is CharactersListProps.FailedCharactersList)
                it.locationsData.setOnClickListener { fragmentProps.navigationActionLocationsList() }
                if (fragmentProps.characterData is CharactersListProps.LoadedCharactersList) {
                    (it.responseList.adapter as CharactersListAdapter).submitList(fragmentProps.characterData.charactersList)
                }
            }
        }
    }
}
