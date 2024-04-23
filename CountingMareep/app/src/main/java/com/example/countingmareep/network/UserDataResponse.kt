package com.example.countingmareep.network

data class UserDataResponse(
    val success: Boolean,
    val msg: String,
    val sessionID: String? = null,
    val name: String? = null,
    val rank: Int = 0,
    val befriended: Int = 0,
    val hoursSlept: Int = 0,
    val birthday: Long = 0L
)
