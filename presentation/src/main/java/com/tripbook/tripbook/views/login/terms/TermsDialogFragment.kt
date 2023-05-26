package com.tripbook.tripbook.views.login.terms


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.tripbook.tripbook.databinding.FragmentTermsDialogBinding
import com.tripbook.tripbook.viewmodel.TermsViewModel

class TermsDialogFragment: DialogFragment() {

    private val viewModel : TermsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = true
    }

    private lateinit var binding: FragmentTermsDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        //이용 약관 상세
        //dialog 안 webview에 맞춰 띄우는 건 어떻게 하는 거야 ? 자꾸 전체 창으로 웹뷰가 로드 돼서
        //일단 html 로드하는 걸로 해놨어 ㅠ.ㅠ
        //각 동의별 if문 추가 여부는 핃백 받고 수정할게용
        val getUrl = "file:///android_asset/termsText/termsText.html"
        var webView : WebView = binding.termsWebView
            webView.loadUrl(getUrl)

        //닫기
        binding.close.setOnClickListener {
            dismiss()
        }

    }



}