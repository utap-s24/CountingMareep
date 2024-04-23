package com.example.countingmareep.network

data class UserResponse(
    val msg: String,
    val sessionID: String? = null // Include sessionID or other fields as necessary
)
