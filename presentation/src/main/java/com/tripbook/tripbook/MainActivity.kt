package com.tripbook.tripbook

import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.AppBarConfiguration
import com.tripbook.base.BaseActivity
import com.tripbook.tripbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    //    private lateinit var account: Auth0
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun init() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHost
        navController = navHost.navController
    }

    override fun onBackPressed() {
        if (navController.backQueue.isEmpty()) {
            finish()
        } else {
            navController.popBackStack()
        }
    }
}