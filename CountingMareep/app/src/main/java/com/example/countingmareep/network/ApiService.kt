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

interface ApiService {
    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("name") name: String,
        @Field("password") password: String
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

    @POST("getUserData")
    @FormUrlEncoded
    fun getUserData(
        @Field("sessionID") sessionID: String
    ): Call<UserDataResponse>

    @POST("updateUserData")
    @FormUrlEncoded
    fun updateUserData(
        @Field("sessionID") sessionID: String,
        @Field("rank") rank: Int,
        @Field("befriended") befriended: Int,
        @Field("hoursSlept") hoursSlept: Int
    ): Call<UserDataResponse>
}
