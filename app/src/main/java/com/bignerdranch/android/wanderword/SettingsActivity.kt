package com.bignerdranch.android.wanderword

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    // Initiate database
    private val databaseHelper = DatabaseHelper(this)
    // Navbar
    private lateinit var myBottomNavbar: MyBottomNavbar
    // Store email
    private lateinit var userEmail: String
    private var userId: Long = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize MyBottomNavbar
        myBottomNavbar = findViewById(R.id.myBottomNavbar)
        // Handle clicks on MyBottomNavbar
        myBottomNavbar.setNavigationItemSelectedListener { menuItemId ->
            when (menuItemId) {
                R.id.action_home -> {
                    // Handle Settings button click
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                R.id.action_friends -> {
                    // Handle Friends button click
                    startActivity(Intent(this, FriendsActivity::class.java))
                }
                R.id.action_collection -> {
                    // Handle Collection button click
                    startActivity(Intent(this, CollectionActivity::class.java))
                }
            }
        }

        // Retrieve the user ID from the intent
        userId = intent.getLongExtra("userId", -1)

        // Get user's email from the database
        userEmail = getUserEmailFromDatabase() // Implement this function to retrieve the email

        // Use userEmail to display the user's email in the UI
        val textViewEmail: TextView = findViewById(R.id.textViewEmail)
        textViewEmail.text = userEmail

    }

    private fun getUserEmailFromDatabase(): String {
        // Retrieve the user's email from the database using the user's ID
        val user = databaseHelper.getUserById(userId)

        return user?.email ?: "Unknown Email"
    }
}