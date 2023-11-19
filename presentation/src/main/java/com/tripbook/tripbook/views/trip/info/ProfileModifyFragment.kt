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
import timber.log.Timber

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
            // 권한 관리는 회원가입 때 했으니까 바로 dialog 띄워도 될 듯!
            ProfileDialogFragment().show(
                requireActivity().supportFragmentManager,
                "Profile Fragment"
            )
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.completeButton.setOnClickListener {
            duplicateCheck()
        }
    }

    private fun updateProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            val imagePath = viewModel.profileUri.value?.let {
                requireContext().getImagePathFromURI(it)
            }
            infoviewModel.updateProfile(imagePath).collect {
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
                    // 중복되는 닉네임이 아니면 update하기!
                    updateProfile()
//                    viewModel.setNickname(binding.nickname.text.toString())
                } else {
                    viewModel.setNicknameValid(binding.nickname.setError(resources.getString(R.string.nickname_duplicate_alert)))
                }
            }
        }
    }


}