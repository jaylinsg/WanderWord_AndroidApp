package com.bignerdranch.android.wanderword

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val splashDelay: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Use a Handler with the main looper to delay the transition to the main activity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the main activity after the splash delay
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish() // Close the splash activity so it's not in the back stack
        }, splashDelay)
    }
}