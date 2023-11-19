package com.tripbook.tripbook.views.trip.info


import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.auth0.android.auth0.BuildConfig
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentMypageBinding
import com.tripbook.tripbook.viewmodel.InfoViewModel
class MypageFragment : BaseFragment<FragmentMypageBinding, InfoViewModel>(R.layout.fragment_mypage) {

    override val viewModel : InfoViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel
        viewModel.getMemberInformation()
        viewModel.setVersion("버전정보 v" + BuildConfig.VERSION_NAME)


        //설정 -> 프로필 수정
        binding.btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_profileModifyFragment)

        }

        //1:1 문의
        binding.askDetail.setOnClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_askFragment)

        }

        //로그아웃
        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_logoutFragment)
        }


    }


}
