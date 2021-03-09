package com.example.calorietracker.userprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.calorietracker.databinding.FragmentEditUserProfileBinding
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.utils.loadImageByUrl
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
            it.profileProgressBar.progress = (user.userIntake / user.plannedIntake * 100).toInt()
            it.weightText.setText(user.userWeight.toString())
            it.userProfileImage.loadImageByUrl(user.userImage)

            it.nameText.doOnTextChanged { text, start, before, count ->
                it.saveButton.visibility = if (user.userName != text.toString()) VISIBLE else GONE
                Log.d("Chacking_text_TAG", "renderUserProfile: ${user.userName != text.toString()}, ${user.userName}, $text")
            }
            it.incomeText.doOnTextChanged { text, start, before, count ->
                it.saveButton.visibility = if (user.plannedIntake.toString() != text.toString()) VISIBLE else GONE
            }
            it.weightText.doOnTextChanged { text, start, before, count ->
                it.saveButton.visibility = if (user.userWeight.toString() != text.toString()) VISIBLE else GONE
                Log.d("Chacking_text_TAG", "renderUserProfile: ${user.userName != text.toString()}, ${user.userName}, $text")
            }
        }
    }
}
