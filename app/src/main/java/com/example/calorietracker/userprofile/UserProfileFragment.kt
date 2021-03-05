package com.example.calorietracker.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentUserProfileBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class UserProfileFragment : Fragment() {

    private var fragmentBinding: FragmentUserProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.profileNavigationView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.profileBottomAppBar.setNavigationOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.profileNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.toProfile -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                R.id.toDailtyIntake -> findNavController().navigate(
                    UserProfileFragmentDirections.actionUserProfileFragmentToDailyIntakeFragment()
                )
            }
//            menuItem.isChecked = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            true
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.editProfile) {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToEditUserProfileFragment())
            }
            true
        }

        return binding.root
    }
}
