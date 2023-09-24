package com.tripbook.tripbook.views.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.tripbook.base.BaseActivity
import com.tripbook.tripbook.MainActivity
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun init() {
        loadSplash()
    }

    private fun loadSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}