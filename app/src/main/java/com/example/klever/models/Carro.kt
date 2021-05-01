package com.example.klever.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class
Carro(
    val id: Long,
    val nome: String,
    val tipo: String,
    val descricao: String,
    @SerializedName("url_foto")
    val urlFoto: String,
    val urlVideo: String,
    val latitude: String,
    val longitude: String
) : Serializable {

}
