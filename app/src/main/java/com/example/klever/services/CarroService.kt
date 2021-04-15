package com.example.klever.services

import com.example.klever.models.Carro
import com.example.klever.models.CarroPost
import com.example.klever.models.ResponseString
import retrofit2.Call
import retrofit2.http.*

interface CarroService {

    @GET("carros")
    fun list(@Header("Authorization")authHeader : String ) : Call<List<Carro>>

    @POST("carros")
    fun save(@Header("Authorization")authHeader : String, @Body loginData: CarroPost?): Call<Carro>

    @PUT("carros/{id}")
    fun edit(@Header("Authorization") authHeader: String,
             @Body loginData: CarroPost?, @Path(value = "id") id: Long?
    ): Call<Carro>

    @DELETE("carros/{id}")
    fun delete(@Header("Authorization") authHeader: String, @Path(value = "id") id: Long?): Call<ResponseString>
}