package com.tripbook.tripbook.views.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.auth0.android.Auth0
import com.tripbook.auth.loginWithBrowser
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentLoginBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding, LoginViewModel>(
    layoutResId = R.layout.fragment_login
){
    private lateinit var auth0: Auth0
    override val viewModel: LoginViewModel by viewModels()

    override fun init() {
        auth0 = Auth0(
            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_client_id),
            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_domain)
        ) // 이걸 Hilt Module 로써 관리를 하면 어떨까..?

        collectProperties()

        requireContext().loginWithBrowser(auth0) {
            viewModel.validateToken(it).start()
        }
    }

    private fun collectProperties() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isValidatedUser.collect {
                    it?.let { validated ->
                        Timber.tag("MyTag").d(validated.toString())
                    }
                }
            }
        }
    }
}