package com.bignerdranch.android.wanderword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)

        // Set click listeners
        btnRegister.setOnClickListener {
            // Handle register button click
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Add your logic for registration here

            // Example: Show a toast message
            Toast.makeText(this, "Registered: $email", Toast.LENGTH_SHORT).show()
        }

        btnLogin.setOnClickListener {
            // Handle login button click
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Add your logic for login here

            // Example: Show a toast message
            Toast.makeText(this, "Logged in: $email", Toast.LENGTH_SHORT).show()
        }
    }
}