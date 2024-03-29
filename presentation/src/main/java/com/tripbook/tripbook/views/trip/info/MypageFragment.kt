package com.tripbook.tripbook.views.trip.info

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.BuildConfig
import com.tripbook.tripbook.databinding.FragmentMypageBinding
import com.tripbook.tripbook.viewmodel.InfoViewModel

class MypageFragment :
    BaseFragment<FragmentMypageBinding, InfoViewModel>(R.layout.fragment_mypage) {

    override val viewModel: InfoViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel
        binding.version.text = getString(R.string.version_name, BuildConfig.VERSION_NAME)

        viewModel.setVersion(binding.version.text.toString())

        //설정 -> 프로필 수정
        binding.btnSetting.setOnClickListener {
            findNavController().navigate(
                MypageFragmentDirections.actionMypageFragmentToProfileModifyFragment()
            )
        }

        //1:1 문의
        binding.askLinearLayout.setOnClickListener {
            findNavController().navigate(
                MypageFragmentDirections.actionMypageFragmentToAskFragment()
            )
        }

        //로그아웃
        binding.btnLogout.setOnClickListener {
            findNavController().navigate(
                MypageFragmentDirections.actionMypageFragmentToLogoutFragment()
            )
        }

        //회원탈퇴
        binding.memberDelete.setOnClickListener {
            MemberDelDialogFragment().show(childFragmentManager, "MemberDelete Fragment")
        }

    }

}
