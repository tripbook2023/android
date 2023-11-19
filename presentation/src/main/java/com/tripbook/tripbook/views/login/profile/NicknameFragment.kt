package com.tripbook.tripbook.views.login.profile

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentNicknameBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class NicknameFragment :
    BaseFragment<FragmentNicknameBinding, LoginViewModel>(R.layout.fragment_nickname) {

    override val viewModel: LoginViewModel by activityViewModels()
    override fun init() {
        binding.viewModel = viewModel
        addNicknameTextWatcher()
        binding.upButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.nicknameButton.setOnClickListener {
            duplicateCheck()
        }
    }

    private fun addNicknameTextWatcher() {
        binding.nickname.doOnTextChanged { text, _, _, _ ->
            viewModel.setNicknameValid(text?.let { binding.nickname.isNicknameValid(it) })
        }
    }

    private fun duplicateCheck() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.validateUserName(binding.nickname.text.toString()).collect {
                if (it) {
                    viewModel.setNickname(binding.nickname.text.toString())
                    findNavController().navigate(R.id.action_nicknameFragment_to_profileFragment)
                } else {
                    viewModel.setNicknameValid(binding.nickname.setError(resources.getString(R.string.nickname_duplicate_alert)))
                }
            }
        }
    }
}