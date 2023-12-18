package com.bignerdranch.android.wanderword

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class CongratulationsFragment : Fragment() {

    private var userId: Long = -1
    private var locationName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_congratulations, container, false)

        val toCollectionButton: Button = view.findViewById(R.id.toCollection)


        //Retrieve userId
        userId = arguments?.getLong("userId", -1) ?: -1
        locationName = arguments?.getString("locationName", "") ?: ""

        toCollectionButton.setOnClickListener {
            val intent = Intent(activity, CollectionActivity::class.java)
            startActivity(intent)
        }



        return view
    }

    // Add collected WanderGem to database
    private fun collectWandergem(wandergem: String) {
        val databaseHelper = DatabaseHelper(requireContext())
        databaseHelper.collectWandergem(userId, wandergem)
    }
}
