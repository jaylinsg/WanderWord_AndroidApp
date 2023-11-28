package com.bignerdranch.android.wanderword

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest
import android.content.Intent


class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    // Handle Settings button click
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.action_friends -> {
                    // Handle Friends button click
                    startActivity(Intent(this, FriendsActivity::class.java))
                    true
                }
                R.id.action_collection -> {
                    // Handle Collection button click
                    startActivity(Intent(this, CollectionActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Enable location tracking
        enableMyLocation()

        // Add markers to specific locations
        addMarkers()
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun addMarkers() {
        // Add markers to specific locations
        val bostonTeaParty = LatLng(42.35232, -71.05129) // Boston Tea Party (Museum)
        val oldNorthChurch = LatLng(42.36642099532497, -71.05436221678688) // Old North Church
        val paulRevereHouse = LatLng(42.363881663210776, -71.05373218980431) // The Paul Revere House
        val bostonCommon = LatLng(42.355224454437334, -71.06581143398586) // Boston Common
        val bostonPublicLibrary = LatLng(42.34942061480286, -71.07821968970795) // Boston Public Library

        googleMap.addMarker(MarkerOptions().position(bostonTeaParty).title("Boston Tea Party (Ships & Museum)"))
        googleMap.addMarker(MarkerOptions().position(oldNorthChurch).title("Old North Church"))
        googleMap.addMarker(MarkerOptions().position(paulRevereHouse).title("The Paul Revere House"))
        googleMap.addMarker(MarkerOptions().position(bostonCommon).title("Boston Common"))
        googleMap.addMarker(MarkerOptions().position(bostonPublicLibrary).title("Boston Public Library"))
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}