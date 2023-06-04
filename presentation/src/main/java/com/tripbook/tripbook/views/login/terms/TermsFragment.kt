package com.tripbook.tripbook.views.login.terms

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTermsBinding
import com.tripbook.tripbook.viewmodel.TermsViewModel

class TermsFragment : BaseFragment<FragmentTermsBinding>(R.layout.fragment_terms) {

    private val viewModel : TermsViewModel by activityViewModels()
    private lateinit var title : String

    override fun init() {
        binding.viewModel = viewModel

        //필수 동의 체크 여부
        binding.termsButton.setOnClickListener {
            isCheck()
        }

        //각 동의별 > 클릭 시 세부 내용 팝업 로드
        binding.termsServiceDialog.setOnClickListener{ //서비스 이용 동의
            title = binding.termsServiceText.text.toString()
            loadTermsDialog(title)
        }

        binding.termsPersonalInfoDialog.setOnClickListener{ //개인 정보 이용 동의
            title = binding.termsPersonalInfoText.text.toString()
            loadTermsDialog(title)
        }

        binding.termsLocationDialog.setOnClickListener {//위치 정보 동의
            title = binding.termsLocationText.text.toString()
            loadTermsDialog(title)
        }

        binding.termsMarketingDialog.setOnClickListener {//마케팅 이용 동의
            title = binding.termsMarketingText.text.toString()
            loadTermsDialog(title)
        }
    } //init

    //이용 동의 타이틀
    private fun loadTermsDialog (termsTitle : String) {
        viewModel.setTermsTitle(termsTitle)
        TermsDialogFragment().show(childFragmentManager, "TermsDialog Fragment")
    }

    //필수 동의 체크 여부
    private fun isCheck()  {
        when {
            viewModel.serviceChecked.value.not() -> {
                Toast.makeText(requireContext(), "서비스 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
            }
            viewModel.personalInfoChecked.value.not() -> {
                Toast.makeText(requireContext(), "개인정보 수집 및 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
            }
            viewModel.locationChecked.value.not() -> {
                Toast.makeText(requireContext(), "위치정보수집 및 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
