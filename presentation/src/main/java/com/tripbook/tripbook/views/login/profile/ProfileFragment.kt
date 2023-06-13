package com.tripbook.tripbook.views.login.profile

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentProfileBinding
import com.tripbook.tripbook.viewmodel.ProfileViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel
        binding.profile.setOnClickListener {
            ProfileDialogFragment().show(
                requireActivity().supportFragmentManager,
                "Profile Fragment"
            )
        }
        binding.profileButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_additionalFragment)
        }
        binding.profileLater.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_additionalFragment)
        }
    }
}