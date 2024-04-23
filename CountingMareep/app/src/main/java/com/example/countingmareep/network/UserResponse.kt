package com.example.countingmareep.network

// This is the response for the user data on login and sign up
data class UserResponse(
    val msg: String,
    val sessionID: String? = null // Include sessionID or other fields as necessary
)
