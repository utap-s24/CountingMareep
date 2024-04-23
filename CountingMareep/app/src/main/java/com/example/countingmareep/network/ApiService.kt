package com.example.countingmareep.network

import retrofit2.Call
import retrofit2.http.*

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
