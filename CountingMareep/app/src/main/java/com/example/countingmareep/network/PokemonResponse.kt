package com.example.countingmareep.network

data class PokemonResponse(
    val _id: String,
    val ownerName: String,
    val name: String,
    val level: Int,
    val pokedexEntry: Int,
    val subSkills: List<Int>,
    val ingredients: List<List<Int>>,
    val nature: String,
    val RP: Int,
    val mainSkillLevel: Int,
    val pokemonID: String,
    val __v: Int
)