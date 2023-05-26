package com.tripbook.tripbook

import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.fragment.app.Fragment
import com.tripbook.tripbook.databinding.ActivityMainBinding
import com.auth0.android.Auth0
import com.tripbook.auth.loginWithBrowser
import com.tripbook.auth.logout
import com.tripbook.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

//    private lateinit var account: Auth0
    private lateinit var navController: NavController

    override fun init() {
//        account = Auth0(
//            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_client_id),
//            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_domain)
//        )
//
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHost
        navController = navHost.navController
//        binding.buttonLogin.setOnClickListener {
//            loginWithBrowser(account)
//        }
//
//        binding.buttonLogout.setOnClickListener {
//            logout(account)
//        }
    }
//
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}