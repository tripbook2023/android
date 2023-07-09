package com.tripbook.tripbook.views.login

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.tripbook.auth.loginWithBrowser
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentLoginBinding
import com.tripbook.tripbook.domain.model.UserLoginStatus
import com.tripbook.tripbook.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    layoutResId = R.layout.fragment_login
) {
    private lateinit var auth0: Auth0
    override val viewModel: LoginViewModel by activityViewModels()

    override fun init() {
        auth0 = Auth0(
            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_client_id),
            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_domain)
        ) // 이걸 Hilt Module 로써 관리를 하면 어떨까..?

        collectProperties()

        requireContext().loginWithBrowser(auth0) {token->
            viewModel.validateToken(token).start()
        }
    }

    private fun collectProperties() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isValidatedUser.collect {
                    it?.let { validated ->
                        when (validated) {
                            UserLoginStatus.STATUS_REQUIRED_AUTH -> findNavController()
                                .navigate(R.id.action_loginFragment_to_profile)

                            UserLoginStatus.STATUS_NORMAL -> {
                                // TODO : 여기서 바로 메인 화면으로 넘어갈 수 있도록
                            }
                        }
                    }
                }
            }
        }
    }
}