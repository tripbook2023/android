package com.tripbook.tripbook

import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.setupWithNavController
import com.tripbook.base.BaseActivity
import com.tripbook.tripbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    //    private lateinit var account: Auth0
    private lateinit var navController: NavController

    override fun init() {

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHost
        navController = navHost.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.isBottomVisible = homeDestinationList.contains(destination.id)
        }
        binding.barBottom.setupWithNavController(navController)
    }
}

private val homeDestinationList = listOf(
    R.id.newsMainFragment,
    R.id.mypageFragment
)