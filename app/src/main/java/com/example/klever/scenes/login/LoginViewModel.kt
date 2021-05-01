package com.example.klever.scenes.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.models.Login
import com.example.klever.models.UserLogin
import com.example.klever.services.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private var mLoginSucess = MutableLiveData<Boolean>()
    var loginSucess = mLoginSucess
    private var mLoginError = MutableLiveData<Boolean>()
    var loginError = mLoginError
    init {
        mLoginSucess.value = false
        mLoginError.value = false
    }
    fun login(user : String, password : String){
        val userLogin = UserLogin(
            username = user,
            password = password
        )
        val remote = RetrofitClient.createService(LoginService::class.java)
        val call : Call<Login> = remote.login(userLogin)
        call.enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                mLoginError.value = true
            }
            override fun onResponse(call: Call<Login>, res: Response<Login>) {
                val login = res.body()
                if(login != null){
                    login?.token?.let { Token.createToken(it) }
                    mLoginSucess.value = true
                }
                else{
                    mLoginError.value = true
                }

            }

        })
    }
}