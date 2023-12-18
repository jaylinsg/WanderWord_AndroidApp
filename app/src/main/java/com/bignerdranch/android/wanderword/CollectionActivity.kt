package com.bignerdranch.android.wanderword

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CollectionActivity : AppCompatActivity() {

    private lateinit var myBottomNavbar: MyBottomNavbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        // Initialize MyBottomNavbar
        myBottomNavbar = findViewById(R.id.myBottomNavbar)
        // Handle clicks on MyBottomNavbar
        myBottomNavbar.setNavigationItemSelectedListener { menuItemId ->
            when (menuItemId) {
                R.id.action_settings -> {
                    // Handle Settings button click
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                R.id.action_friends -> {
                    // Handle Friends button click
                    startActivity(Intent(this, FriendsActivity::class.java))
                }
                R.id.action_home -> {
                    // Handle Home button click
                    startActivity(Intent(this, HomeActivity::class.java))
                }
            }
        }
    }
}