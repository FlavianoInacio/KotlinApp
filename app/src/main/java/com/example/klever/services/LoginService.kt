package com.example.klever.services

import com.example.klever.models.Login
import com.example.klever.models.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    @Headers("Content-Type: application/json")
    fun login(@Body loginData: UserLogin): Call<Login>
}