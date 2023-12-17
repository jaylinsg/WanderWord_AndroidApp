package com.bignerdranch.android.wanderword

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CollectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    // Handle Home button click
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.action_friends -> {
                    // Handle Friends button click
                    startActivity(Intent(this, FriendsActivity::class.java))
                    true
                }
                R.id.action_settings -> {
                    // Handle Collection button click
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}