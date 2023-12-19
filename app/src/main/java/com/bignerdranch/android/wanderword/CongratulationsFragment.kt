package com.bignerdranch.android.wanderword

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class CongratulationsFragment : Fragment() {

    private var userId: Long = -1
    private var locationName: String = ""
    private lateinit var blueWandergemImageView: ImageView
    private lateinit var redWandergemImageView: ImageView
    private lateinit var greenWandergemImageView: ImageView
    private lateinit var yellowWandergemImageView: ImageView
    private lateinit var purpleWandergemImageView: ImageView

    val sharedViewModel: GlobalVars by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Access shared information
        locationName = sharedViewModel.locationName
        //val userId = sharedViewModel.userId

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_congratulations, container, false)

        val toCollectionButton: Button = view.findViewById(R.id.toCollection)

        // Initialize rewards images
        blueWandergemImageView = view.findViewById(R.id.blueWandergemImageView)
        greenWandergemImageView = view.findViewById(R.id.greenWandergemImageView)
        purpleWandergemImageView = view.findViewById(R.id.purpleWandergemImageView)
        redWandergemImageView = view.findViewById(R.id.redWandergemImageView)
        yellowWandergemImageView = view.findViewById(R.id.yellowWandergemImageView)

        // Find the TextView by its ID
        val debugTextView: TextView = view.findViewById(R.id.debugTextView)

        // Update the text of the TextView with relevant variable values
        debugTextView.text = locationName


        // Use determineWandergem to set the correct image visibility
        when ((locationName)) {
            "Boston Tea Party (Ships & Museum)" -> blueWandergemImageView.visibility = View.VISIBLE
            "Old North Church" -> greenWandergemImageView.visibility = View.VISIBLE
            "The Paul Revere House" -> purpleWandergemImageView.visibility = View.VISIBLE
            "Boston Common" -> redWandergemImageView.visibility = View.VISIBLE
            "Boston Public Library" -> yellowWandergemImageView.visibility = View.VISIBLE
            else -> blueWandergemImageView.visibility = View.VISIBLE
        }

        toCollectionButton.setOnClickListener {
            val intent = Intent(activity, CollectionActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    // Add collected Wandergem to database
    private fun collectWandergem(wandergem: String) {
        val databaseHelper = DatabaseHelper(requireContext())
        databaseHelper.collectWandergem(userId, wandergem)
    }

    fun setLocationInfo(userId: Long, locationName: String) {
        this.userId = userId
        this.locationName = locationName
    }
}
