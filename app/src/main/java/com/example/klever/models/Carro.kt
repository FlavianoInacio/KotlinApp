package com.example.klever.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class
Carro: Serializable {
    val id: Long =0
    val nome: String = ""
    val tipo: String = ""
    val descricao: String = ""
    @SerializedName("url_foto") val urlFoto: String = ""
    val urlVideo: String = ""
    val latitude: String = ""
    val longitude: String = ""

}
