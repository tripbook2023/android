package com.tripbook.tripbook.views.login.profile

import android.content.Context
import android.graphics.Rect
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentNicknameBinding
import com.tripbook.tripbook.viewmodel.ProfileViewModel

class NicknameFragment : BaseFragment<FragmentNicknameBinding>(R.layout.fragment_nickname) {

    private val viewModel: ProfileViewModel by activityViewModels()

    private val layoutListener = object: OnGlobalLayoutListener{
        override fun onGlobalLayout() {
            val r = Rect()
            binding.contentView.getWindowVisibleDisplayFrame(r)
            val screenHeight = binding.contentView.rootView.height

            val keypadHeight = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) {
                if (!viewModel.isKeyboardUp.value) {
                    viewModel.setKeyboard(true)
                    binding.root.setOnClickListener {
                        hideKeyboard()
                    }
                }
            } else {
                if (viewModel.isKeyboardUp.value) viewModel.setKeyboard(false)
            }
            binding.contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }
    override fun init() {
        nicknameTextWatcher()
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

    private fun nicknameTextWatcher() {
        binding.nickname.doOnTextChanged { text, _, _, _ ->
            viewModel.setNicknameValid(binding.nickname.isNicknameValid(text!!))
        }
    }

    // 서버 확인 따로 -> 버튼 누르면
    private fun duplicateCheck(): Boolean {
        // 닉네임 중복확인 API 호출
        return true
    }

    private fun hideKeyboard() {
        requireActivity().currentFocus?.let {
            val inputManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}