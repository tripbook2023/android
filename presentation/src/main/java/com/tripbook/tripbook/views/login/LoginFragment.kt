package com.tripbook.tripbook.views.login

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.tripbook.tripbook.R
import com.tripbook.tripbook.TripBookBaseFragment
import com.tripbook.tripbook.databinding.FragmentLoginBinding
import com.tripbook.tripbook.domain.model.UserLoginStatus
import com.tripbook.tripbook.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : TripBookBaseFragment<FragmentLoginBinding, LoginViewModel>(
    layoutRes = R.layout.fragment_login
) {
    private lateinit var auth0: Auth0
    override val viewModel: LoginViewModel by activityViewModels()

    override fun init() {
        auth0 = Auth0(
            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_client_id),
            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_domain)
        ) // 이걸 Hilt Module 로써 관리를 하면 어떨까..?

        collectProperties()
        loginWithAuth0()
    }

    private fun loginWithAuth0() {
        lifecycleScope.launch {
            try {
                val credential = WebAuthProvider.login(account = auth0)
                    .withScheme("demo")
                    .withTrustedWebActivity()
                    .await(requireContext())
                println(credential)
                viewModel.validateToken(credential.accessToken).start()
            } catch(e: AuthenticationException) {
                e.printStackTrace()
            }
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
                                findNavController().navigate(
                                    R.id.action_loginFragment_to_travel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}