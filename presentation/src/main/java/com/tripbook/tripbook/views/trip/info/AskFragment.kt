package com.tripbook.tripbook.views.trip.info


import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentAskBinding
import com.tripbook.tripbook.viewmodel.InfoViewModel

class AskFragment : BaseFragment<FragmentAskBinding, InfoViewModel>(R.layout.fragment_ask) {

    override val viewModel : InfoViewModel by activityViewModels()

    override fun init() {

        val webView  = binding.webView
        val url = "https://docs.google.com/forms/d/12EdphtlVAt3doOG7ns3DElvDmW3XQA9tkBTQ2tBITyI/edit"

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.loadUrl(url)

        webView.webViewClient = WebViewClient()

        webView.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && webView.canGoBack()) {
                webView.goBack()
                true
            } else {
                false
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.completeButton.setOnClickListener {
            webView.visibility = View.VISIBLE
            binding.completeButton.visibility = View.GONE
        }
    }


}