package com.example.klever.scenes.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.klever.infra.ProcessResult
import com.example.klever.models.UserLogin
import com.example.klever.repository.LoginRepository

class LoginViewModel : ViewModel() {
    private var loginRepository: LoginRepository = LoginRepository()

    fun login(user: String, password: String): MutableLiveData<ProcessResult> = loginRepository.login(UserLogin(
        username = user,
        password = password
    ))

}