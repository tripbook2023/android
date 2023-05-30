package com.tripbook.tripbook.views.login.terms

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope

import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTermsBinding
import com.tripbook.tripbook.viewmodel.TermsViewModel
import kotlinx.coroutines.launch

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
        viewModel.setTermsTitle(termsTitle)
        TermsDialogFragment().show(childFragmentManager, "TermsDialog Fragment")
    }

    private var onCheckedChanged = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        when (buttonView.id) {
            R.id.all_terms_agree -> viewModel.onAllTermsCheckedChanged(isChecked)
            R.id.terms_service -> viewModel.setServiceChecked(isChecked)
            R.id.terms_personal_info -> viewModel.setPersonalInfoChecked(isChecked)
            R.id.terms_location -> viewModel.setLocationChecked(isChecked)
            R.id.terms_marketing -> viewModel.setMarketingChecked(isChecked)
        }
    }

    //필수 동의 체크 여부
    private fun isCheck()  {
        if(!viewModel.serviceChecked.value) {
            Toast.makeText(requireContext(), "서비스 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
        } else if(!viewModel.personalInfoChecked.value) {
            Toast.makeText(requireContext(), "개인정보 수집 및 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
        } else {
            if(!viewModel.locationChecked.value) {
                Toast.makeText(requireContext(), "위치정보수집 및 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

     @BindingAdapter("buttonColor")
        fun setButtonColor(button: Button, termsChk: Boolean?) {
         termsChk?.let { isChecked ->
             val context = button.context
             val backgroundColor = if (isChecked) {
                 context.getColor(R.color.tripBook_main)
             } else {
                 context.getColor(R.color.base)
             }

             val textColor = if (isChecked) {
                 context.getColor(R.color.white)
             } else {
                 context.getColor(R.color.white)
             }
             button.setBackgroundColor(backgroundColor)
             button.setTextColor(textColor)
         }

 }
}
