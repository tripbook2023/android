package com.tripbook.tripbook.views.login.profile

import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentAdditionalBinding
import com.tripbook.tripbook.viewmodel.AdditionalProfileViewModel

class AdditionalFragment :
    BaseFragment<FragmentAdditionalBinding, AdditionalProfileViewModel>(R.layout.fragment_additional) {

    override val viewModel: AdditionalProfileViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel
        addAgeTextWatcher()
        binding.completeButton.setOnClickListener {
        }
    }

    private fun addAgeTextWatcher() {
        binding.age.doOnTextChanged { text, _, _, _ ->
            viewModel.setAgeValid(binding.age.isAgeValid(text!!))
        }
        binding.age.doAfterTextChanged {
            if (binding.age.text.toString() == "") {
                viewModel.setIcon(0)
            }
        }
    }

}
