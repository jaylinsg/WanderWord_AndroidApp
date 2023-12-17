package com.bignerdranch.android.wanderword

data class User (
    val id: Long,
    val email: String,
    val password: String,
    val friends: List<String> = emptyList(),
    val collectedItems: List<String> = emptyList(),
    val username: String = ""
) {
    // Allows user creation with only email n pass
    constructor(id: Long, email: String, password: String) : this(id, email, password, emptyList(), emptyList(), "")
}