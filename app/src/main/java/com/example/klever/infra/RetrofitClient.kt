package com.example.klever.infra

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){

    companion object{
        private lateinit var  retrofit: Retrofit
        private val baseUrl = "http://192.168.1.69:8181/api/v1/"
        //private val baseUrl = "https://carros-springboot.herokuapp.com/api/v2/"
        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if(!Companion::retrofit.isInitialized){
                retrofit = Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }
            return retrofit
        }

        fun <T>createService(serviceClass: Class<T>): T{
            return getRetrofitInstance()
                .create(serviceClass)
        }

    }
}