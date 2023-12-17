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

    // Initiate database
    private val databaseHelper = DatabaseHelper(this)

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

    // User chooses register option
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

    // User chooses login option
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

    // User attempts to register
    fun onRegisterClick(view: View) {
        val email = editTextEmail.text.toString()
        val confirmEmail = editTextConfirmEmail.text.toString()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()

        // Perform registration logic
        if (validateRegistrationInputs(email, confirmEmail, password, confirmPassword)) {
            // Check if the user already exists in the database
            val existingUser = databaseHelper.getUserByEmail(email)

            if (existingUser == null) {
                // User does not exist, proceed with registration
                databaseHelper.insertUser(email, password)

                // Registration is valid, perform registration actions
                Toast.makeText(this, "Registered: $email", Toast.LENGTH_SHORT).show()
                // Add your registration logic here

                // You might want to navigate to the login UI after successful registration
                showLoginUI()
            } else {
                // User already exists
                Toast.makeText(this, "User with this email already exists", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Registration is not valid, show an error message or handle accordingly
            Toast.makeText(this, "Invalid registration input", Toast.LENGTH_SHORT).show()
        }
    }


    // User attempts to login
    fun onLoginClick(view: View) {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        // Perform login logic
        if (validateLoginInputs(email, password)) {
            // Check if the user exists in the database
            val user = databaseHelper.getUserByEmail(email)

            if (user != null && user.password == password) {
                // Login is successful, perform login actions
                Toast.makeText(this, "Logged in: $email", Toast.LENGTH_SHORT).show()

                // Start the HomeActivity when the login is successful
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                // User does not exist or password is incorrect
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
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