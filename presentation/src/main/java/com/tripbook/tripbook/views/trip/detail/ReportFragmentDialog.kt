package com.tripbook.tripbook.views.trip.detail

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.tripbook.base.BaseDialogFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentReportDialogBinding
import com.tripbook.tripbook.viewmodel.ArticleViewModel
import kotlinx.coroutines.launch

class ReportFragmentDialog : BaseDialogFragment<FragmentReportDialogBinding, ArticleViewModel>(R.layout.fragment_report_dialog) {

    override val viewModel: ArticleViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel

        binding.dialogClose.setOnClickListener {
            dismiss()
            viewModel.setReportResult(false)
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
            viewModel.setReportResult(false)
        }
        binding.btnReport.setOnClickListener {
            if (binding.reportContent.text.isEmpty()) {
                Toast.makeText(requireContext(), "게시글 신고사유를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.reportArticle(binding.reportContent.text.toString()).collect{
                        viewModel.setReportResult(it)
                        if(!it) Toast.makeText(requireContext(), "이미 신고된 게시글이거나 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}