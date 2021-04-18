package com.example.klever.infra


class Token private constructor(){
    companion object{
        private lateinit var  token: String
        fun createToken (token : String) : String {
             if(!Companion::token.isInitialized){
                 this.token = token
             }
             return this.token
         }
    }
}