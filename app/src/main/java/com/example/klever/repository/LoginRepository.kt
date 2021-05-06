package com.example.klever.repository


import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.listener.ApiLoginListener
import com.example.klever.models.Login
import com.example.klever.models.UserLogin
import com.example.klever.services.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    fun login(userLogin: UserLogin, listener : ApiLoginListener) {
        val remote = RetrofitClient.createService(LoginService::class.java)
        val call: Call<Login> = remote.login(userLogin)
        call.enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                listener.error(t.message.toString())
            }
            override fun onResponse(call: Call<Login>, res: Response<Login>) {
                val login = res.body()
                if(login !=null){
                    listener.sucess(login)
                }else{
                    listener.error("Algo deu errado")
                }
            }
        })
    }

}