package com.example.klever.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.klever.infra.ResultadoLogin
import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.models.Login
import com.example.klever.models.UserLogin
import com.example.klever.services.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    fun login(userLogin: UserLogin): MutableLiveData<ResultadoLogin> {
        val liveData = MutableLiveData<ResultadoLogin>()
        val remote = RetrofitClient.createService(LoginService::class.java)
        val call: Call<Login> = remote.login(userLogin)
        call.enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                println(t.message)
                liveData.postValue(ResultadoLogin.ERROR)
            }
            override fun onResponse(call: Call<Login>, res: Response<Login>) {
                val login = res.body()
                if (login != null) {
                    login?.token?.let { Token.createToken(it) }
                    liveData.postValue(ResultadoLogin.SUCESSO)
                }
                else{
                    liveData.postValue(ResultadoLogin.ERROR)
                }

            }
        })
        return liveData

    }

}