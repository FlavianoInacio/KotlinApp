package com.example.klever.repository

import androidx.lifecycle.MutableLiveData
import com.example.klever.infra.ProcessResult
import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.listener.ApiCarroListener
import com.example.klever.listener.ApiLoginListener
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

    fun getCarros(listener : ApiCarroListener){
        val call: Call<List<Carro>> = mRemote.list(mToken)
        call.enqueue(object : Callback<List<Carro>> {
            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                listener.error(ProcessResult.ERROR)
            }
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                val carros: List<Carro>? = response.body()
                if(carros != null){
                    listener.sucess(carros)
                }
                else{
                    listener.error(ProcessResult.ERROR)
                }
            }
        })
    }

    fun save(carroPost: CarroPost, listener: ApiCarroListener) {
        val call: Call<Carro> = serviceVerify(mRemote, carroPost)
        call.enqueue(object : Callback<Carro> {
            override fun onFailure(call: Call<Carro>, t: Throwable) {
                listener.error(ProcessResult.ERROR)
            }
            override fun onResponse(call: Call<Carro>, response: Response<Carro>) {
                val carro = response.body()
                if (carro?.id != null) {
                    listener.sucess(ProcessResult.SUCESS)
                } else {
                    listener.error(ProcessResult.ERROR)
                }
            }
        })
    }

    fun delete(id: Long?, listener: ApiCarroListener) {
        val call: Call<ResponseString> = mRemote.delete(mToken, id)
        call.enqueue(object : Callback<ResponseString> {
            override fun onFailure(call: Call<ResponseString>, t: Throwable) {
                listener.error(ProcessResult.ERROR)
            }
            override fun onResponse(call: Call<ResponseString>, response: Response<ResponseString>) {
                listener.sucess(ProcessResult.SUCESS)
            }

        })
    }

    private fun serviceVerify(remote: CarroService, carroPost: CarroPost): Call<Carro> =
        if (carroPost.id != null) remote.edit(
            mToken,
            carroPost,
            carroPost.id
        ) else remote.save(mToken, carroPost)
}