package com.example.klever.scenes.carros

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.klever.infra.ProcessResult
import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.listener.ApiCarroListener
import com.example.klever.models.Carro
import com.example.klever.models.CarroPost
import com.example.klever.models.ResponseString
import com.example.klever.repository.CarroRepository
import com.example.klever.services.CarroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarrosViewModel : ViewModel() {
    var carrosLiveData = MutableLiveData<List<Carro>>()
    var processResultLiveData = MutableLiveData<ProcessResult>()

    var listener = object : ApiCarroListener {
        override fun sucess(carros: List<Carro>) = carrosLiveData.postValue(carros)
        override fun sucess(processResult: ProcessResult) = processResultLiveData.postValue(processResult)
        override fun error(processResult: ProcessResult) = processResultLiveData.postValue(processResult)

    }
    private val carroRepository: CarroRepository = CarroRepository()
    fun listCarros(): MutableLiveData<List<Carro>> {
        carroRepository.getCarros(listener)
        return carrosLiveData
    }
    fun delete(id: Long?): MutableLiveData<ProcessResult>  {
        carroRepository.delete(id,listener)
        return processResultLiveData
    }

    fun save(nome: String, descricao: String, id: Long?): MutableLiveData<ProcessResult> {
        carroRepository.save(
            CarroPost(
                url_foto = null,
                id = id,
                nome = nome,
                descricao = descricao
            ), listener
        )
        return processResultLiveData
    }

}