package com.example.klever.listener
import com.example.klever.models.Login

interface ApiLoginListener {
    fun sucess(login: Login)
    fun error(message : String)
}