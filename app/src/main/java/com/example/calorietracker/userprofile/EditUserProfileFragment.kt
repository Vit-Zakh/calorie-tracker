package com.example.calorietracker.userprofile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserProfileFragment : Fragment() {

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
}
