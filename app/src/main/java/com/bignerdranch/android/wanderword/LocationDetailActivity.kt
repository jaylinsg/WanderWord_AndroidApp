package com.bignerdranch.android.wanderword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class LocationDetailActivity : AppCompatActivity() {

    private var userId: Long = -1
    private var locationName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        // Add the GameFragment dynamically
        if (savedInstanceState == null) {
            addGameFragment()
        }

        // Retrieve data from Intent
        val locationName = intent.getStringExtra("LOCATION_NAME")
        val locationDetails = intent.getStringExtra("LOCATION_DETAILS")
        userId = intent.getLongExtra("userId", -1)

        // Update UI elements with data
        val locationNameTextView: TextView = findViewById(R.id.locationNameTextView)
        val locationDetailsTextView: TextView = findViewById(R.id.locationDetailsTextView)
        val locationNameLabel: TextView = findViewById(R.id.locationNameLabel)
        val locationDetailsLabel: TextView = findViewById(R.id.locationDetailsLabel)

        locationNameLabel.text = getString(R.string.location_name_label)
        locationDetailsLabel.text = getString(R.string.location_details_label)
        locationNameTextView.text = locationName
        locationDetailsTextView.text = locationDetails


    }
        private fun addGameFragment() {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

            val gameFragment = GameFragment.newInstance(userId, locationName)

            // Pass the selected word to the GameFragment
            intent.getStringExtra("SELECTED_WORD")?.let {
                val bundle = Bundle()
                bundle.putString("SELECTED_WORD", it)
                gameFragment.arguments = bundle
            }

            fragmentTransaction.replace(R.id.gameFragmentContainer, gameFragment)

            // Commit the transaction
            fragmentTransaction.commit()
        }
}
