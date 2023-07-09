package com.tripbook.tripbook.views.login.profile

import android.app.DatePickerDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentAdditionalBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import java.util.Date

class AdditionalFragment :
    BaseFragment<FragmentAdditionalBinding, LoginViewModel>(R.layout.fragment_additional) {

    override val viewModel: LoginViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel
        setUpBirthday()
        binding.completeButton.setOnClickListener {
            signUp()// 서버에 전체 회원가입 정보 전송
        }
    }

    private fun signUp() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signUp().collect {
                if (it) {
                    // 회원가입 성공 페이지로 이동
                    findNavController().navigate(R.id.action_additionalFragment_to_signUpSuccessFragment)
                } else {
                    // 회원가입 실패 메시지 띄우기
                    Timber.tag("Sign Up").d("회원가입 실패")
                }
            }
        }
    }

    private fun setUpBirthday() {
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(),
                { _, y, m, d ->
                    binding.textBirth.text = String.format(resources.getString(R.string.additional_birth_text), y, m+1, d)
                    viewModel.setBirthday(binding.textBirth.text.toString())
                }, year, month, day
            )
        datePickerDialog.datePicker.maxDate = Date().time
        binding.textBirth.setOnClickListener {
            datePickerDialog.show()
        }
    }
}
