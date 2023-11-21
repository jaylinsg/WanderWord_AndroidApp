package com.bignerdranch.android.wanderword

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val splashDelay: Long = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Use a Handler to delay the transition to the main activity
        Handler().postDelayed({
            // Start the main activity after the splash delay
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish() // Close the splash activity so it's not in the back stack
        }, splashDelay)
    }
}