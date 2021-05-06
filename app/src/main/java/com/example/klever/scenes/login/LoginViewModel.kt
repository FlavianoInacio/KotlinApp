package com.example.klever.scenes.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.klever.infra.ProcessResult
import com.example.klever.infra.Token
import com.example.klever.listener.ApiLoginListener
import com.example.klever.models.Carro
import com.example.klever.models.Login
import com.example.klever.models.UserLogin
import com.example.klever.repository.LoginRepository

class LoginViewModel : ViewModel() {
    private var loginRepository: LoginRepository = LoginRepository()

    fun login(user: String, password: String): MutableLiveData<ProcessResult> {
        var processResult = MutableLiveData<ProcessResult>()
        loginRepository.login(
            UserLogin(
                username = user,
                password = password
            ),
            object : ApiLoginListener {
                override fun sucess(login: Login) {
                    Token.createToken(login.token)
                    processResult.postValue(ProcessResult.SUCESS)
                }
                override fun error(message: String) {
                    processResult.postValue(ProcessResult.ERROR)
                }

            }
        )
        return processResult
    }

}