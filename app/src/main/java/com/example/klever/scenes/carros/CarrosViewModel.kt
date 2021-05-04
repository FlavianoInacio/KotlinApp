package com.example.klever.scenes.carros

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.klever.infra.ProcessResult
import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.models.Carro
import com.example.klever.models.CarroPost
import com.example.klever.models.ResponseString
import com.example.klever.repository.CarroRepository
import com.example.klever.services.CarroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarrosViewModel : ViewModel() {
    private val carroRepository: CarroRepository = CarroRepository()

    fun listCarros(): MutableLiveData<List<Carro>> = carroRepository.getCarros()

    fun delete(id: Long?): MutableLiveData<ProcessResult> = carroRepository.delete(id)

    fun save(nome: String, descricao: String, id: Long?): MutableLiveData<ProcessResult> =
        carroRepository.save(
            CarroPost(
                url_foto = null,
                id = id,
                nome = nome,
                descricao = descricao
            )
        )
}