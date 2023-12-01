package com.bignerdranch.android.wanderword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class LocationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        // Retrieve data from Intent
        val locationName = intent.getStringExtra("LOCATION_NAME")
        val locationDetails = intent.getStringExtra("LOCATION_DETAILS")
        // TODO: Retrieve other data if needed

        // Update UI elements with data
        val locationNameTextView: TextView = findViewById(R.id.locationNameTextView)
        val locationDetailsTextView: TextView = findViewById(R.id.locationDetailsTextView)
        val locationNameLabel: TextView = findViewById(R.id.locationNameLabel)
        val locationDetailsLabel: TextView = findViewById(R.id.locationDetailsLabel)

        locationNameLabel.text = getString(R.string.location_name_label)
        locationDetailsLabel.text = getString(R.string.location_details_label)
        locationNameTextView.text = locationName
        locationDetailsTextView.text = locationDetails
        // Update other UI elements with data
    }
}
