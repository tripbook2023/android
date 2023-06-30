package com.tripbook.tripbook

import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.tripbook.base.BaseActivity
import com.tripbook.tripbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    //    private lateinit var account: Auth0
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun init() {
//        account = Auth0(
//            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_client_id),
//            getString(com.tripbook.tripbook.libs.auth.R.string.com_auth_domain)
//        )
//

//        binding.buttonLogin.setOnClickListener {
//            loginWithBrowser(account)
//        }
//
//        binding.buttonLogout.setOnClickListener {
//            logout(account)
//        }

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHost
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        navController = navHost.navController

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.mainToolbar.title.text = destination.label
            if (destination.parent!!.startDestinationId != destination.id) {
                toolbar.setNavigationIcon(com.tripbook.tripbook.core.design.R.drawable.icn_before_24)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp(appBarConfiguration)
    }
}