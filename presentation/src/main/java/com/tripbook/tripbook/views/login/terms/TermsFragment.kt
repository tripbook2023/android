package com.tripbook.tripbook.views.login.terms

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
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

        binding.allTermsAgree.setOnCheckedChangeListener(onCheckedChanged) //전체 동의
        binding.termsService.setOnCheckedChangeListener(onCheckedChanged)//서비스
        binding.termsPersonalInfo.setOnCheckedChangeListener(onCheckedChanged)  //개인정보
        binding.termsLocation.setOnCheckedChangeListener(onCheckedChanged)  //위치정보
        binding.termsMarketing.setOnCheckedChangeListener(onCheckedChanged) //마게팅

    } //init

    //이용 동의 타이틀
    private fun loadTermsDialog (termsTitle : String) {
        viewModel.getTermsTitle(termsTitle)
        TermsDialogFragment().show(childFragmentManager, "TermsDialog Fragment")
    }

    //아니 이 함수 진짜 줄이고 싶거든... 근데 방법을 잘 모르겠어
    // 핃백 주면 참고해서 수정할게 ㅠ 내가 생각해도 쓸데없이 길다 ㅠ
    private var onCheckedChanged = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (buttonView != null) {
            if(isChecked) {
                when(buttonView.id) {
                    R.id.all_terms_agree -> {
                            binding.termsService.isChecked = true
                            binding.termsPersonalInfo.isChecked = true
                            binding.termsLocation.isChecked = true
                            binding.termsMarketing.isChecked = true
                            setButtonColor(true)
                    }
                }
            } else {
                when(buttonView.id) {
                    R.id.all_terms_agree -> {
                            binding.termsService.isChecked = false
                            binding.termsPersonalInfo.isChecked = false
                            binding.termsLocation.isChecked = false
                            binding.termsMarketing.isChecked = false
                            setButtonColor(false)
                    }
                }
            }
            //서비스, 개인정보, 위치 체크 시
            if(binding.termsService.isChecked && binding.termsPersonalInfo.isChecked && binding.termsLocation.isChecked ) {
                setButtonColor(true)
            } else {
                setButtonColor(false)
            }

        }
    }

    //필수 동의 체크 여부
    // 근데 viewModel로 옮기라구 했잖아 체크여부 기능이 끝인데 어떤 식으로 viewmodel에 넣는 게 좋은 거야~?
    // 이용 동의 title 같은 거는 옮겼는데 체크박스는 로직이 잘 안 떠오르네 ㅠ 그리고 찾아봤을 땐 fragment에선
    // onclick 안 먹힌다고 해서 setOnCheckedChangeListener이거 쓴 거거든?
    // 만약 viewmodel로 옮기고 나서는 데이터를 뭘로 가져와야 하는 거야? 맨날 난 한 화면에서 다 처리해서 좀 어렵네. 핃백 주는 대로 공부하고 수정할게!
    private fun isCheck() {
        if(!binding.termsPersonalInfo.isChecked) {
            Toast.makeText(requireContext(), "서비스 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
        } else if(!binding.termsPersonalInfo.isChecked) {
            Toast.makeText(requireContext(), "개인정보 수집 및 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
        } else {
            if(!binding.termsLocation.isChecked) {
                Toast.makeText(requireContext(), "위치정보수집 및 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setButtonColor(chk : Boolean) {
        if(chk) {
            context?.let { binding.termsButton.setBackgroundColor(it.getColor(R.color.tripBook_main)) }
            context?.let { binding.termsButton.setTextColor(it.getColor(R.color.white)) }
        } else {
            context?.let { binding.termsButton.setBackgroundColor(it.getColor(R.color.base)) }
        }

    }

}


