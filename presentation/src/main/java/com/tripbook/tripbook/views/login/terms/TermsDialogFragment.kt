package com.tripbook.tripbook.views.login.terms


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.tripbook.tripbook.R
import com.tripbook.tripbook.data.model.TermsURL
import com.tripbook.tripbook.databinding.FragmentTermsDialogBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel

class TermsDialogFragment: DialogFragment() {

    private val viewModel : LoginViewModel by activityViewModels()

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
        val termsURL: TermsURL = viewModel.getTermsURL(termsTitle)
        webView.loadUrl(termsURL.url)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //닫기
        binding.close.setOnClickListener {
            dismiss()
        }
    }
}