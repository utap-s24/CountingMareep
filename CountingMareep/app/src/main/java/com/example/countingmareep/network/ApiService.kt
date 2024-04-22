package com.example.countingmareep.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}
