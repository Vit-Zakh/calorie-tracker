package com.example.calorietracker.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.calorietracker.databinding.FragmentEditUserProfileBinding

class EditUserProfileFragment : Fragment() {

    private var fragmentBinding: FragmentEditUserProfileBinding? = null

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
