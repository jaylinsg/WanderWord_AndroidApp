package com.bignerdranch.android.wanderword

data class User (
    val id: Long,
    val email: String,
    val password: String,
    val friends: List<String>,
    val collectedItems: List<String>,
    val username: String
)