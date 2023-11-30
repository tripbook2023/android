package com.tripbook.tripbook.views.trip.info

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.tripbook.auth.logout
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.NavGraphDirections
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentLogoutBinding
import com.tripbook.tripbook.viewmodel.InfoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LogoutFragment : BaseFragment<FragmentLogoutBinding, InfoViewModel>(R.layout.fragment_logout) {

    override val viewModel : InfoViewModel by activityViewModels()
    private lateinit var auth0: Auth0

    override fun init() {

        auth0 = Auth0(
            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_client_id),
            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_domain)
        )

        viewLifecycleOwner.lifecycleScope.launch {
            requireContext().logout(auth0).collect {
                viewModel.logout()
                delay(3000)
                findNavController().navigate(NavGraphDirections.actionGlobalLoginFragment())

            }
        }
    }
}
