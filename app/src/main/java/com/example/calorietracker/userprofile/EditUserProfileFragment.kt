package com.example.calorietracker.userprofile

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentEditUserProfileBinding
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.utils.calculateProgressPercent
import com.example.calorietracker.utils.loadImageByUrl
import com.example.calorietracker.utils.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserProfileFragment : Fragment() {

    private var fragmentBinding: FragmentEditUserProfileBinding? = null
    private val viewModel: EditUserProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditUserProfileBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        viewModel.currentUserData.observe(viewLifecycleOwner) { userData ->
            if (userData is DailyIntakeProps.LoadedUser) renderUserProfile(userData.user)
        }
        viewModel.canBeSaved.observe(viewLifecycleOwner) {
            fragmentBinding?.saveButton?.showIf(it)
        }

        binding.userProfileImage.setOnClickListener {
            launchUserImageIntent.launch(READ_EXTERNAL_STORAGE)
        }

        binding.profileBackgroundImage.setOnClickListener {
            launchBackgroundIntent.launch(READ_EXTERNAL_STORAGE)
        }

        return binding.root
    }

    private fun renderUserProfile(user: DailyIntakeProps.UserProps) {
        fragmentBinding?.let {
            it.nameText.setText(user.userName)
            it.incomeText.setText(user.plannedIntake.toString())
            it.profileProgressBar.progress.calculateProgressPercent(
                user.userIntake,
                user.plannedIntake
            )
            it.profileProgressText.text = resources.getString(
                R.string.user_calories_progress_text_one_line,
                user.userIntake,
                user.plannedIntake
            )
            it.weightText.setText(user.userWeight.toString())
            it.ageText.setText(user.userAge.toString())
            it.profileBackgroundImage.loadImageByUrl(user.backgroundImage)
            it.userProfileImage.loadImageByUrl(user.userImage)

            it.saveButton.setOnClickListener {
                saveUserProfile()
                viewModel.saveChanges()
                findNavController().navigate(EditUserProfileFragmentDirections.actionEditUserProfileFragmentToUserProfileFragment())
            }

            it.nameText.doAfterTextChanged { nameValue ->
                if (!TextUtils.isEmpty(nameValue))
                    viewModel.changeUserName(nameValue.toString())
            }

            it.weightText.doAfterTextChanged { weightValue ->
                if (!TextUtils.isEmpty(weightValue) && TextUtils.isDigitsOnly(weightValue))
                    viewModel.changeUserWeight(weightValue.toString().toFloat())
                else viewModel.changeUserWeight(null)
            }

            it.ageText.doAfterTextChanged { ageValue ->
                if (!TextUtils.isEmpty(ageValue) && TextUtils.isDigitsOnly(ageValue))
                    viewModel.changeUserAge(ageValue.toString().toInt())
                else viewModel.changeUserAge(null)
            }

            it.incomeText.doAfterTextChanged { intakeValue ->
                if (!TextUtils.isEmpty(intakeValue) && TextUtils.isDigitsOnly(intakeValue))
                    viewModel.changeUserIntake(intakeValue.toString().toFloat())
                else viewModel.changeUserIntake(null)
            }
        }
    }

    private fun saveUserProfile() {
        viewModel.saveChanges()
    }

    private fun getStorageContent(grantedImage: (Uri) -> Unit) =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            if (imageUri == null) return@registerForActivityResult
            else grantedImage(imageUri)
        }

    private val userProfileImageRequest = getStorageContent { imageUri ->
        viewModel.changeProfilePreview(imageUri.toString())
    }

    private val backgroundImageRequest = getStorageContent { imageUri ->
        viewModel.changeBackgroundPreview(imageUri.toString())
    }

    private fun askPermission(permission: String, whenGranted: () -> Unit) =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> whenGranted()
                !shouldShowRequestPermissionRationale(permission) -> {
                    Toast.makeText(context, "cannot proceed without permission", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(context, "cannot proceed without permission", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    private val launchUserImageIntent =
        askPermission(READ_EXTERNAL_STORAGE) { userProfileImageRequest.launch("image/*") }
    private val launchBackgroundIntent =
        askPermission(READ_EXTERNAL_STORAGE) { backgroundImageRequest.launch("image/*") }
}
