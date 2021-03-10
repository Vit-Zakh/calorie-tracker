package com.example.calorietracker.userprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentEditUserProfileBinding
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.utils.loadImageByUrl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserProfileFragment : Fragment() {

    private val GALLERY_REQUEST_CODE = 1234

    private var fragmentBinding: FragmentEditUserProfileBinding? = null
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
    }

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

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.galleryNavigationView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.userProfileImage.setOnClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            pickImageFromGallery()
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
                userImageUrl = "https://cataas.com/cat",
                userBackgroundUrl = "https://i.picsum.photos/id/1018/3914/2935.jpg?hmac=3N43cQcvTE8NItexePvXvYBrAoGbRssNMpuvuWlwMKg"
            )
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    fragmentBinding?.userProfileImage?.loadImageByUrl(data?.data.toString())
                } else Toast.makeText(context, "Whoops!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
