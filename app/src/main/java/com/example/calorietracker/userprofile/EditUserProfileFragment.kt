package com.example.calorietracker.userprofile

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentEditUserProfileBinding
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.utils.loadImageByUrl
import com.example.calorietracker.utils.showIf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditUserProfileFragment : Fragment() {

    private var fragmentBinding: FragmentEditUserProfileBinding? = null
    private val viewModel: UserProfileViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var userProfileUrl: String
    private lateinit var backgroundUrl: String

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditUserProfileBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        viewModel.currentUserData.observe(viewLifecycleOwner) { userData ->
            when (userData) {
                is DailyIntakeProps.LoadedUser -> renderUserProfile(userData.user)
                else -> Toast.makeText(context, "placeholder", Toast.LENGTH_SHORT).show()
            }
        }

        userProfileUrl = sharedPreferences.getString("USER_IMAGE_URL", null).toString()
        backgroundUrl = sharedPreferences.getString("USER_BACKGROUND_URL", null).toString()

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.galleryNavigationView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

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
            it.profileProgressBar.progress = (user.userIntake / user.plannedIntake!! * 100).toInt()
            it.profileProgressText.text = resources.getString(
                    R.string.user_calories_progress_text_one_line,
                    user.userIntake,
                    user.plannedIntake
            )
            it.weightText.setText(user.userWeight.toString())
            it.ageText.setText(user.userAge)
            it.profileBackgroundImage.loadImageByUrl(user.backgroundImage)
            it.userProfileImage.loadImageByUrl(user.userImage)

            it.nameText.doOnTextChanged { text, _, _, _ ->
                it.saveButton.visibility = if (user.userName != text.toString()) VISIBLE else GONE
            }
            it.incomeText.doOnTextChanged { text, _, _, _ ->
                it.saveButton.visibility = if (user.plannedIntake.toString() != text.toString()) VISIBLE else GONE
            }
            it.weightText.doOnTextChanged { text, _, _, _ ->
                it.saveButton.visibility = if (user.userWeight.toString() != text.toString()) VISIBLE else GONE
            }
            it.ageText.doOnTextChanged { text, _, _, _ ->
                it.saveButton.visibility = if (user.userWeight.toString() != text.toString()) VISIBLE else GONE
            }

            it.saveButton.setOnClickListener {
                saveUserProfile()
                findNavController().navigate(EditUserProfileFragmentDirections.actionEditUserProfileFragmentToUserProfileFragment())
            }
        }
    }

    private fun saveUserProfile() {
        fragmentBinding?.let {
            viewModel.saveToSharedPreferences(
                    userName = it.nameText.text.toString(),
                    userAge = it.ageText.text.toString(),
                    userWeight = it.weightText.text.toString(),
                    userIncome = it.incomeText.text.toString(),
                    userImageUrl = userProfileUrl,
                    userBackgroundUrl = backgroundUrl
            )
        }
    }

    private fun getStorageContent(grantedImage: (Uri) -> Unit) = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
        if (imageUri == null) return@registerForActivityResult
        else grantedImage(imageUri)
    }

    private val userProfileImageRequest = getStorageContent { imageUri ->
        fragmentBinding?.userProfileImage?.loadImageByUrl(imageUri.toString())
        fragmentBinding?.saveButton?.showIf(imageUri.toString() != userProfileUrl)
        userProfileUrl = imageUri.toString()
    }

    private val backgroundImageRequest = getStorageContent { imageUri ->
        fragmentBinding?.profileBackgroundImage?.loadImageByUrl(imageUri.toString())
        fragmentBinding?.saveButton?.showIf(imageUri.toString() != backgroundUrl)
        backgroundUrl = imageUri.toString()
    }

    private fun askPermission(permission: String, whenGranted: () -> Unit) =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                when {
                    granted -> whenGranted()
                    !shouldShowRequestPermissionRationale(permission) -> {
                        Toast.makeText(context, "cannot proceed without permission", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "cannot proceed without permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }

    private val launchUserImageIntent = askPermission(READ_EXTERNAL_STORAGE) { userProfileImageRequest.launch("image/*") }
    private val launchBackgroundIntent = askPermission(READ_EXTERNAL_STORAGE) { backgroundImageRequest.launch("image/*") }
}
