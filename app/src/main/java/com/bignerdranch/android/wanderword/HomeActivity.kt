package com.bignerdranch.android.wanderword

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
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
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.Marker


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

        // Set the initial camera position to the user's location if available
        setInitialCameraPosition()

        // Set up marker click listeners
        googleMap.setOnMarkerClickListener {  marker ->
            handleMarkerClick(marker)
            true // Return true to indicate that the click has been handled
        }
    }

    private fun handleMarkerClick(marker: Marker) {
        // Retrieve information about the clicked marker
        val locationName = marker.title

        // Open LocationDetailActivity to display location details
        val intent = Intent(this, LocationDetailActivity::class.java).apply {
            putExtra("LOCATION_NAME", locationName)
            putExtra("LOCATION_DETAILS", locationName?.let { getDetailsForLocation(it) })
            // TODO: Add other relevant data
        }
        startActivity(intent)
    }

    private fun setInitialCameraPosition() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // If location permission is granted, get the last known location
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    // Move the camera to the user's current location
                    val userLocation = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                }
            }
        }
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
        // Add markers to historic locations
        val bostonTeaParty = LatLng(42.35232, -71.05129) // Boston Tea Party (Museum)
        val oldNorthChurch = LatLng(42.36642099532497, -71.05436221678688) // Old North Church
        val paulRevereHouse = LatLng(42.363881663210776, -71.05373218980431) // The Paul Revere House
        val bostonCommon = LatLng(42.355224454437334, -71.06581143398586) // Boston Common
        val bostonPublicLibrary = LatLng(42.34942061480286, -71.07821968970795) // Boston Public Library

        val teaPartyMarker = googleMap.addMarker(
            MarkerOptions().position(bostonTeaParty).title("Boston Tea Party (Ships & Museum)")
        )

        val oldNorthChurchMarker = googleMap.addMarker(
            MarkerOptions().position(oldNorthChurch).title("Old North Church")
        )

        val paulRevereHouseMarker = googleMap.addMarker(
            MarkerOptions().position(paulRevereHouse).title("The Paul Revere House")
        )

        val bostonCommonMarker = googleMap.addMarker(
            MarkerOptions().position(bostonCommon).title("Boston Common")
        )

        val bostonLibraryMarker = googleMap.addMarker(
            MarkerOptions().position(bostonPublicLibrary).title("Boston Public Library")
        )

        // Set up info window click listener
/*
    googleMap.setOnInfoWindowClickListener { marker ->
       // Handle InfoWindow click
       val locationName = marker.title
       val locationDetails: String = locationName?.let { getDetailsForLocation(it) } ?: "Details not available"
       // Open LocationDetailActivity to display location details
       val intent = Intent(this, LocationDetailActivity::class.java).apply {
           putExtra("LOCATION_NAME", locationName)
           putExtra("LOCATION_DETAILS", locationDetails)
           //TODO: Add game availability
       }
       startActivity(intent)
   }

   // Set InfoWindow contents (description and game availability)

   googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindodapter {
       override fun getInfoContents(marker: Marker): View? {
           // Inflate custom layout
           val view = layoutInflater.inflate(R.layout.info_window_layout, null, false)

           // Find views in the layout
           val textLocationName: TextView = view.findViewById(R.id.textLocationName)
           //val textLocationDescription: TextView = view.findViewById(R.id.textLocationDescription)
           val textGameAvailability: TextView = view.findViewById(R.id.textGameAvailability)

           // Set data to views
           textLocationName.text = marker?.title ?:"Default Location Name" // TODO: Adjust location name
           //textLocationDescription.text = getString(R.string.location_description_template, marker?.title) // Replace with actual description
           textGameAvailability.text = "Game Available"  // Replace with actual game availability status


           return view
       }

       override fun getInfoWindow(marker: Marker): View? {
           // You can create a custom layout for InfoWindow here
           return null
       }
   })

    */
}

private fun getDetailsForLocation(locationName: String): String {
   // Replace this logic with the actual details retrieval based on the location name
   return when (locationName) {
       "Boston Tea Party (Ships & Museum)" -> "The Boston Tea Party Ships & Museum is a pivotal landmark that commemorates one of the defining moments in America’s march toward independence. For visitors, it serves as a living history lesson, immersing them in the sights, sounds, and stories of the Boston Tea Party, the historic event on December 16, 1773. The year 2023 marks the 250th anniversary of this momentous act of defiance, making a visit all the more significant.\n" +
               "\n" +
               "The museum masterfully captures the spirit of rebellion and political protest that marked the era. The Boston Tea Party was an audacious response to the Tea Act passed by the British Parliament in 1773. American colonists, chafing under the legislation, which imposed a tax on tea and maintained a monopoly for the British East India Company, staged an innovative protest.\n"
       "Old North Church" -> "The Old North Church, officially known as Christ Church in the City of Boston, holds a pivotal place in the annals of American history. As the city’s oldest surviving church building, it’s most notably recognized as the location where the famous “one if by land, two if by sea” signal is said to have been sent. This phrase is synonymous with Paul Revere’s midnight ride, a critical event at the onset of the Revolutionary War.\n" +
               "\n" +
               "Today, the Old North Church is a National Historic Landmark and a key feature on Boston’s Freedom Trail. It continues to attract visitors from around the globe, drawn by its remarkable history and well-preserved Colonial architecture. A visit to the Old North Church offers a unique opportunity to step back in time and relive a critical chapter of America’s past while enjoying a magnificent example of early American craftsmanship.\n"
       "The Paul Revere House" -> "The Paul Revere House, located in the North End of Boston, serves as a tangible link to the city’s storied Colonial past. It stands as the oldest building in downtown Boston and is a must-see landmark for visitors looking to delve into the history of the American Revolution.\n" +
               "\n" +
               "Paul Revere himself is an iconic figure in American history, recognized for his pivotal role in the nascent stages of the Revolutionary War. Born in Boston in 1735, Revere was a silversmith by trade and a patriot at heart. He was a central figure in the city’s resistance against British authority, most notably conducting the legendary “Midnight Ride” in 1775 to alert Colonial militias of incoming British forces. Revere’s home, now a revered historic site, was built around 1680 and became Revere’s residence in 1770.\n"
       "Boston Common" -> "As the oldest city park in America, the Boston Common has held a central role in Boston’s history for centuries. Established in 1634, it served as communal pasture land, where locals could graze their livestock. However, by 1756, a portion of the Common was set aside to become the Central Burying Ground, providing a resting place for many of Boston’s early inhabitants.\n" +
               "\n" +
               "In the fervor of the 18th century, the Boston Common became a hotbed for Revolutionary activity. It hosted numerous public events that stoked the fires of the American Revolution, becoming a symbol of the city’s defiance and resilience. During the British occupation of Boston, Redcoats made the Common their encampment, transforming this public space into a stronghold for eight years.\n"
       "Boston Public Library" -> "The Boston Public Library, established in 1848, holds the distinction of being the first large free municipal library in the United States. The library took a revolutionary step forward by becoming the first public library to lend books, democratizing access to knowledge and learning. It continued to pioneer new ideas in library services by opening the first branch library, and was the first to create a dedicated children’s room, further emphasizing its commitment to inclusivity and accessibility.\n" +
               "\n" +
               "Furthermore, the Boston Public Library is a landmark that should be on any visitor’s itinerary. Its significance in the city’s history and its ongoing contributions to the community make it a must-visit destination for those seeking to fully experience the spirit of Boston.\n"
       else -> "Details not available"
   }
}


private fun showToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


companion object {
   private const val LOCATION_PERMISSION_REQUEST_CODE = 1
}
}