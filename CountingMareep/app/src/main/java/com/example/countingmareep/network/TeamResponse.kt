package com.example.countingmareep.network

data class TeamResponse(
    val _id: String,
    val ownerName: String,
    val teamName: String,
    val pok1Entry: Int,
    val pok2Entry: Int,
    val pok3Entry: Int,
    val pok4Entry: Int,
    val pok5Entry: Int,
    val __v: Int
)