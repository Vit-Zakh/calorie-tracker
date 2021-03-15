package com.example.calorietracker.userprofile

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentUserProfileBinding
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.utils.loadImageByUrl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var fragmentBinding: FragmentUserProfileBinding? = null
    private val viewModel: UserProfileViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        makeToolBarTransparent()

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
                    makeToolBarVisible()
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
//        binding.topAppBar.

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

    private fun makeToolBarTransparent() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            requireActivity().window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun makeToolBarVisible() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.decorView.systemUiVisibility = View.VISIBLE
        requireActivity().window.statusBarColor = resources.getColor(R.color.primary_back)
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = requireActivity().window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onDetach() {
        super.onDetach()
        makeToolBarVisible()
    }
}
