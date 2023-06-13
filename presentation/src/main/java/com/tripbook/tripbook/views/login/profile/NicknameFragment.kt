package com.tripbook.tripbook.views.login.profile

import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentNicknameBinding
import com.tripbook.tripbook.viewmodel.ProfileViewModel

class NicknameFragment :
    BaseFragment<FragmentNicknameBinding, ProfileViewModel>(R.layout.fragment_nickname) {

    override val viewModel: ProfileViewModel by activityViewModels()

    override fun init() {
        addNicknameTextWatcher()
        binding.contentView.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
        binding.viewModel = viewModel
        binding.nicknameButton.setOnClickListener {
            if (duplicateCheck()) {
                viewModel.setNickname(binding.nickname.text.toString())
                findNavController().navigate(R.id.action_nicknameFragment_to_profileFragment)
            } else {
                viewModel.setNicknameValid(binding.nickname.setError(resources.getString(R.string.nickname_duplicate_alert)))
            }
        }
    }

    private fun addNicknameTextWatcher() {
        binding.nickname.doOnTextChanged { text, _, _, _ ->
            viewModel.setNicknameValid(binding.nickname.isNicknameValid(text!!))
        }
        binding.nickname.doAfterTextChanged {
            if (binding.nickname.text.toString() == "") {
                viewModel.setIcon(0)
            }
        }
    }

    // 서버 확인 따로 -> 버튼 누르면
    private fun duplicateCheck(): Boolean {
        // 닉네임 중복확인 API 호출
        return true
    }

    override fun onStop() {
        binding.contentView.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
        super.onStop()
    }
}