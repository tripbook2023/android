package com.tripbook.tripbook.views.trip.info

import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentProfileModifyBinding
import com.tripbook.tripbook.viewmodel.InfoViewModel
import com.tripbook.tripbook.viewmodel.LoginViewModel
import com.tripbook.tripbook.views.login.profile.ProfileDialogFragment
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileModifyFragment : BaseFragment<FragmentProfileModifyBinding, LoginViewModel>(R.layout.fragment_profile_modify) {

    override val viewModel : LoginViewModel by activityViewModels()
    private val infoviewModel : InfoViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun init() {
        binding.viewModel = viewModel

        addNicknameTextWatcher()

        binding.profile.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.completeButton.setOnClickListener {
            duplicateCheck()
            updateProfile()
        }
    }

    private fun updateProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            infoviewModel.updateProfile().collect {
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
            viewModel.setNicknameValid(binding.nickname.isNicknameValid(text!!))
        }
        binding.nickname.doAfterTextChanged {
            if (binding.nickname.text.toString() == "") {
                viewModel.setIcon(0)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                ProfileDialogFragment().show(
                    requireActivity().supportFragmentManager,
                    "Profile Fragment"
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "권한 동의를 하셔야 프로필 이미지를 설정할 수 있습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun duplicateCheck() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.validateUserName(binding.nickname.text.toString()).collect{
                if(it){
                    viewModel.setNickname(binding.nickname.text.toString())
                }else{
                    viewModel.setNicknameValid(binding.nickname.setError(resources.getString(R.string.nickname_duplicate_alert)))
                }
            }
        }
    }


}