package com.tripbook.tripbook.views.login.terms


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTermsDialogBinding
import com.tripbook.tripbook.viewmodel.TermsViewModel

class TermsDialogFragment: DialogFragment() {

    private val viewModel : TermsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
        isCancelable = true
    }

    private lateinit var binding: FragmentTermsDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermsDialogBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        //webview
        val webView: WebView = binding.termsWebView

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }

        val termsTitle = viewModel.termsTitle.value
        val url = getTermsURL(termsTitle)
        webView.loadUrl(url)

        return binding.root
    }

    private fun getTermsURL(termsTitle: String): String {
        return when (termsTitle) { //임시 url 설정
            "서비스 이용약관 동의" -> "https://www.naver.com/"
            "개인정보 수집 및 이용 동의" -> "https://www.youtube.com/"
            "위치정보수집 및 이용동의" -> "https://www.google.com/webhp?hl=ko&sa=X&ved=0ahUKEwiK9-vYnJ3_AhXOCd4KHaFFByoQPAgI"
            else -> "https://www.daum.net/"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //닫기
        binding.close.setOnClickListener {
            dismiss()
        }

    }


}