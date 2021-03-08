package com.example.calorietracker.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.calorietracker.databinding.FragmentEditUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserProfileFragment : Fragment() {

    private var fragmentBinding: FragmentEditUserProfileBinding? = null
    private val viewModel: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditUserProfileBinding.inflate(inflater, container, false)
        fragmentBinding = binding
        return binding.root
    }
}
