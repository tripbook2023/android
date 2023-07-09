package com.tripbook.tripbook.views.login.profile

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentSignupSuccessBinding

class SignUpSuccessFragment : Fragment() {

    private var _binding: FragmentSignupSuccessBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_signup_success, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLoading()
    }

    private fun startLoading() {
        // n초 후 메인 홈으로 이동
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}