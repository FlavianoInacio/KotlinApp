package com.example.klever.models

import com.google.gson.annotations.SerializedName

data class CarroPost (
    @SerializedName("id") val id: Long?,
    @SerializedName("nome") val nome: String?,
    @SerializedName("descricao") val descricao: String?,
    @SerializedName("url_foto") val url_foto: String?
)