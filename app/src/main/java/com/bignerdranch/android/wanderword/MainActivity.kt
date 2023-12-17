package com.bignerdranch.android.wanderword

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var logoImageView: ImageView
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var btnRegister2: Button
    private lateinit var btnLogin2: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextConfirmEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        logoImageView = findViewById(R.id.logoImageView)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister2 = findViewById(R.id.btnRegister2)
        btnLogin2 = findViewById(R.id.btnLogin2)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextConfirmEmail = findViewById(R.id.editTextConfirmEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)

        // Set click listeners
        btnRegister.setOnClickListener {
            // Handle register button click
            showRegistrationUI()
        }

        btnLogin.setOnClickListener {
            // Handle login button click
            showLoginUI()
        }
    }

    private fun showRegistrationUI() {
        // Remove Logo
        logoImageView.visibility = View.GONE

        // Change buttons
        btnRegister.visibility = View.GONE
        btnRegister2.visibility = View.VISIBLE
        btnLogin.visibility = View.VISIBLE
        btnLogin2.visibility = View.GONE

        // Show registration UI elements
        editTextEmail.visibility = View.VISIBLE
        editTextConfirmEmail.visibility = View.VISIBLE
        editTextPassword.visibility = View.VISIBLE
        editTextConfirmPassword.visibility = View.VISIBLE
    }

    private fun showLoginUI() {
        // Remove Logo
        logoImageView.visibility = View.GONE

        // Change buttons
        btnRegister.visibility = View.VISIBLE
        btnRegister2.visibility = View.GONE
        btnLogin.visibility = View.GONE
        btnLogin2.visibility = View.VISIBLE

        // Show login UI elements
        editTextEmail.visibility = View.VISIBLE
        editTextPassword.visibility = View.VISIBLE

        // Hide registration UI elements
        editTextConfirmEmail.visibility = View.GONE
        editTextConfirmPassword.visibility = View.GONE
    }

    fun onRegisterClick(view: View) {
        val email = editTextEmail.text.toString()
        val confirmEmail = editTextConfirmEmail.text.toString()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()

        // Perform registration logic
        if (validateRegistrationInputs(email, confirmEmail, password, confirmPassword)) {
            // Registration is valid, perform registration actions
            Toast.makeText(this, "Registered: $email", Toast.LENGTH_SHORT).show()
            // Add your registration logic here
        } else {
            // Registration is not valid, show an error message or handle accordingly
            Toast.makeText(this, "Invalid registration input", Toast.LENGTH_SHORT).show()
        }
    }

    fun onLoginClick(view: View) {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        // Perform login logic
        if (validateLoginInputs(email, password)) {
            // Login is valid, perform login actions
            Toast.makeText(this, "Logged in: $email", Toast.LENGTH_SHORT).show()
            // Start the HomeActivity when the login is successful
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            // Login is not valid, show an error message or handle accordingly
            Toast.makeText(this, "Invalid login input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateRegistrationInputs(
        email: String,
        confirmEmail: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        // Add your registration input validation logic here
        return email.isNotEmpty() && confirmEmail.isNotEmpty() &&
                password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                email == confirmEmail && password == confirmPassword
    }

    private fun validateLoginInputs(email: String, password: String): Boolean {
        // Add your login input validation logic here
        return email.isNotEmpty() && password.isNotEmpty()
    }
}