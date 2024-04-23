package com.example.countingmareep.network

import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.NatureData
import com.example.countingmareep.ui.box.modify.SubSkill
import retrofit2.Call
import retrofit2.http.*

data class CreatePokemonRequest(
    val sessionID: String,
    val name: String,
    val level: Int,
    val pokedexEntry: Int,
    val subSkills: List<Int>,
    val ingredients: List<List<Int>>,
    val nature: String,
    val RP: Int,
    val mainSkillLevel: Int,
    val pokemonID: String
)

data class CreateTeamRequest(
    val sessionID: String,
    val teamName: String,
    val pokemon1ID: String,
    val pokemon2ID: String, // "" if Pokemon not Defined
    val pokemon3ID: String,
    val pokemon4ID: String,
    val pokemon5ID: String
)

data class SinglePokemonRequest(
    val pokemonID: String
)

data class BasicSessionRequest(
    val sessionID: String
)

interface ApiService {
    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("name") name: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @POST("logout")
    @FormUrlEncoded
    fun logout(
        @Field("sessionID") sessionID: String
    ): Call<UserResponse>

    @POST("signUp")
    @FormUrlEncoded
    fun createUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("birthday") birthday: Long,
        @Field("rank") rank: Int = 1,
        @Field("befriended") befriended: Int = 0,
        @Field("hoursSlept") hoursSlept: Int = 0
    ): Call<UserResponse>

    @POST("getPokemon")
    @FormUrlEncoded
    fun getPokemon(
        @Field("sessionID") sessionID: String
    ): Call<List<PokemonResponse>>

    @POST("createPokemon")
    fun createPokemon(@Body request: CreatePokemonRequest): Call<UserResponse>

    @POST("updatePokemon")
    fun updatePokemon(@Body request: CreatePokemonRequest): Call<UserResponse>

    @POST("getSinglePokemon")
    fun getSinglePokemon(@Body request: SinglePokemonRequest): Call<PokemonResponse>

    @POST("createTeam")
    fun createTeam(@Body request: CreateTeamRequest): Call<UserResponse>

    @POST("getAllTeams")
    fun getAllTeams(@Body request: BasicSessionRequest): Call<List<TeamResponse>>

    @POST("getUserData")
    @FormUrlEncoded
    fun getUserData(
        @Field("sessionID") sessionID: String
    ): Call<UserDataResponse>

    @POST("updateUserData")
    @FormUrlEncoded
    fun updateUserData(
        @Field("sessionID") sessionID: String,
        @Field("name") name: String,
        @Field("rank") rank: Int,
        @Field("befriended") befriended: Int,
        @Field("hoursSlept") hoursSlept: Int,
        @Field("birthday") birthday: Long
    ): Call<UserDataResponse>
}
