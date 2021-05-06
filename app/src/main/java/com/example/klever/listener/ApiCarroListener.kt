package com.example.klever.listener

import com.example.klever.infra.ProcessResult
import com.example.klever.models.Carro

interface ApiCarroListener {
    fun sucess(carros : List<Carro>)
    fun error(rocessResult : ProcessResult)
    fun sucess(processResult : ProcessResult)
}