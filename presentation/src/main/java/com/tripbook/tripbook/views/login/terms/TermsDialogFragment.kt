package com.tripbook.tripbook.views.login.terms

import android.os.Bundle
import android.view.Gravity
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.tripbook.base.BaseDialogFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.data.model.TermsURL
import com.tripbook.tripbook.databinding.FragmentTermsDialogBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel

class TermsDialogFragment :
    BaseDialogFragment<FragmentTermsDialogBinding, LoginViewModel>(R.layout.fragment_terms_dialog) {

    override val viewModel: LoginViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
        isCancelable = true
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun init() {
        binding.viewModel = viewModel

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

        binding.close.setOnClickListener {
            dismiss()
        }
    }
}