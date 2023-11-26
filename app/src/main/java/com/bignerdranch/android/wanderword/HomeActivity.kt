package com.bignerdranch.android.wanderword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    // Handle Settings button click
                    // Example: startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.action_friends -> {
                    // Handle Friends button click
                    // Example: startActivity(Intent(this, FriendsActivity::class.java))
                    true
                }
                R.id.action_collection -> {
                    // Handle Collection button click
                    // Example: startActivity(Intent(this, CollectionActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}