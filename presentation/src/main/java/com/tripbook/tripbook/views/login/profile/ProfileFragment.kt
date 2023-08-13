package com.tripbook.tripbook.views.login.profile

import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentProfileBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel


class ProfileFragment :
    BaseFragment<FragmentProfileBinding, LoginViewModel>(R.layout.fragment_profile) {

    override val viewModel: LoginViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun init() {
        binding.viewModel = viewModel

        binding.upButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.profile.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
        }
        binding.profileButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_additionalFragment)
        }
        binding.profileLater.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_additionalFragment)
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                ProfileDialogFragment().show(
                    requireActivity().supportFragmentManager, "Profile Fragment"
                )
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.profile_authority_text), Toast.LENGTH_SHORT
                ).show()
            }
        }

}
