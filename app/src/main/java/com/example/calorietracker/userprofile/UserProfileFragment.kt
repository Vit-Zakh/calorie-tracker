package com.example.calorietracker.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentUserProfileBinding
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.utils.ToolBarManager
import com.example.calorietracker.utils.loadImageByUrl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var fragmentBinding: FragmentUserProfileBinding? = null
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var toolBarManager: ToolBarManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolBarManager = ToolBarManager(requireActivity())
        toolBarManager.makeToolBarTransparent()

        val binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        viewModel.currentUserData.observe(viewLifecycleOwner) { userData ->
            when (userData) {
                is DailyIntakeProps.LoadedUser -> renderUserProfile(userData.user)
                else -> Toast.makeText(context, "placeholder", Toast.LENGTH_SHORT).show()
            }
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.profileNavigationView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.profileBottomAppBar.setNavigationOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.profileNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.toProfile -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                R.id.toDailtyIntake -> {
//                    makeToolBarVisible()
                    toolBarManager.makeToolBarVisible()
                    findNavController().navigate(
                        UserProfileFragmentDirections.actionUserProfileFragmentToDailyIntakeFragment()
                    )
                }
            }
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            true
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.editProfile) {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToEditUserProfileFragment())
            }
            true
        }

        binding.topAppBar.overflowIcon = resources.getDrawable(R.drawable.ic_settings_24)
        return binding.root
    }

    private fun renderUserProfile(user: DailyIntakeProps.UserProps) {
        fragmentBinding?.let {
            it.profileUserName.text = user.userName
            it.profileProgressBar.progress = (user.userIntake / user.plannedIntake!! * 100).toInt()
            it.weightTile.text = user.userWeight.toString()
            it.userProfileImage.loadImageByUrl(user.userImage)
            it.profileProgressText.text = resources.getString(
                R.string.user_calories_progress_text_one_line,
                user.userIntake,
                user.plannedIntake
            )
            it.ageTile.text = user.userAge.toString()
            it.profileBackgroundImage.loadImageByUrl(user.backgroundImage)
        }
    }

    override fun onDetach() {
        super.onDetach()
        toolBarManager.makeToolBarVisible()
    }
}
