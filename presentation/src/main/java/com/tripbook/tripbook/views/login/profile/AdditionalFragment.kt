package com.tripbook.tripbook.views.login.profile

import android.content.Context
import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentAdditionalBinding
import com.tripbook.tripbook.viewmodel.AdditionalProfileViewModel

class AdditionalFragment : BaseFragment<FragmentAdditionalBinding>(R.layout.fragment_additional) {

    private val viewModel: AdditionalProfileViewModel by activityViewModels()
    private val layoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
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
        }
    }

    override fun init() {
        binding.viewModel = viewModel
        ageTextWatcher()
        binding.contentView.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
        binding.completeButton.setOnClickListener {
        }
    }

    // 버튼 바인딩
    // customEdit 완료되면 버튼 valid
    private fun ageTextWatcher() {
        binding.age.doOnTextChanged { text, _, _, _ ->
            viewModel.setAgeValid(binding.age.isAgeValid(text!!))
        }
    }

    override fun onStop() {
        binding.contentView.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
        super.onStop()
    }

    fun hideKeyboard() {
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