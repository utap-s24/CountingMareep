package com.example.countingmareep.network

data class UserResponse(
    val success: Boolean,
    val msg: String,
    val sessionID: String? = null // Include sessionID or other fields as necessary
)
