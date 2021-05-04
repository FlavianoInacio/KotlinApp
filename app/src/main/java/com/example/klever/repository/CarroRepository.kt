package com.example.klever.repository

import androidx.lifecycle.MutableLiveData
import com.example.klever.infra.ProcessResult
import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.models.Carro
import com.example.klever.models.CarroPost
import com.example.klever.models.ResponseString
import com.example.klever.services.CarroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarroRepository {
    private val mToken: String = "Bearer ${Token.createToken("")}"
    private val mRemote = RetrofitClient.createService(CarroService::class.java)
    private var liveDataResult = MutableLiveData<ProcessResult>()

    fun getCarros(): MutableLiveData<List<Carro>> {
        var liveData = MutableLiveData<List<Carro>>()
        val call: Call<List<Carro>> = mRemote.list(mToken)
        call.enqueue(object : Callback<List<Carro>> {
            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                liveData.postValue(null)
            }

            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                val carros: List<Carro>? = response.body()
                liveData.postValue(carros)
            }
        })
        return liveData
    }

    fun save(carroPost: CarroPost): MutableLiveData<ProcessResult> {
        val call: Call<Carro> = serviceVerify(mRemote, carroPost)
        call.enqueue(object : Callback<Carro> {
            override fun onFailure(call: Call<Carro>, t: Throwable) {
                liveDataResult.postValue(ProcessResult.ERROR)
            }

            override fun onResponse(call: Call<Carro>, response: Response<Carro>) {
                val carro = response.body()
                if (carro?.id != null) {
                    liveDataResult.postValue(ProcessResult.SUCESSO)
                } else {
                    liveDataResult.postValue(ProcessResult.ERROR)
                }
            }
        })
        return liveDataResult
    }

    fun delete(id: Long?) : MutableLiveData<ProcessResult> {
        val call: Call<ResponseString> = mRemote.delete(mToken, id)
        call.enqueue(object : Callback<ResponseString> {
            override fun onFailure(call: Call<ResponseString>, t: Throwable) {
                liveDataResult.postValue(ProcessResult.ERROR)
            }

            override fun onResponse(call: Call<ResponseString>, response: Response<ResponseString>) {
                liveDataResult.postValue(ProcessResult.SUCESSO)
            }

        })
        return liveDataResult
    }

    private fun serviceVerify(remote: CarroService, carroPost: CarroPost): Call<Carro> =
        if (carroPost.id != null) remote.edit(
            mToken,
            carroPost,
            carroPost.id
        ) else remote.save(mToken, carroPost)
}