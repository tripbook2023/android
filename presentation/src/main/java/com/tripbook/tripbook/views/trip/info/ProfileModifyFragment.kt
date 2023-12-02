package com.tripbook.tripbook.views.trip.info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentProfileModifyBinding
import com.tripbook.tripbook.utils.getImagePathFromURI
import com.tripbook.tripbook.viewmodel.InfoViewModel
import com.tripbook.tripbook.viewmodel.LoginViewModel
import com.tripbook.tripbook.views.login.profile.ProfileDialogFragment
import kotlinx.coroutines.launch

class ProfileModifyFragment :
    BaseFragment<FragmentProfileModifyBinding, LoginViewModel>(R.layout.fragment_profile_modify) {

    override val viewModel: LoginViewModel by activityViewModels()
    private val infoviewModel: InfoViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun init() {
        binding.viewModel = viewModel
        binding.infoViewModel = infoviewModel

        addNicknameTextWatcher()

        binding.profile.setOnClickListener {
            ProfileDialogFragment().show(
                requireActivity().supportFragmentManager,
                "Profile Fragment"
            )
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.completeButton.setOnClickListener {

            infoviewModel.nickCheck(binding.nickname.text.toString())
            val nickChk = infoviewModel.nicknameChecked.value

            if (nickChk) { //기존 닉넴과 바뀐 닉넴 다를 때만 체크해줘도 update 에서 걸리는 듯 ? 백엔드 추후 문의
                duplicateCheck()
            }

            updateProfile()
        }

    }

    private fun updateProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            val imagePath = viewModel.profileUri.value?.let {
                requireContext().getImagePathFromURI(it)
            }
            val name = binding.nickname.text.toString()
            infoviewModel.updateProfile(name, imagePath).collect {
                if (it) {
                    //내정보로 다시 돌아가기
                    Timber.tag("updateProfile").d("프로필 변경 성공")
                    findNavController().popBackStack()
                } else {
                    // 프로필 변경 실패
                    Timber.tag("error updateProfile").d("프로필 변경 실패")
                }
            }
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
                } else {
                    viewModel.setNicknameValid(binding.nickname.setError(resources.getString(R.string.nickname_duplicate_alert)))
                }
            }
        }
    }


}