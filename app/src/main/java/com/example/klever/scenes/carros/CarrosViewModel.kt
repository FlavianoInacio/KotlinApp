package com.example.klever.scenes.carros

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.models.Carro
import com.example.klever.models.CarroPost
import com.example.klever.models.ResponseString
import com.example.klever.services.CarroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarrosViewModel : ViewModel() {
    private val mToken : String = "Bearer ${Token.createToken("")}"
    private val mRemote = RetrofitClient.createService(CarroService::class.java)
    private  var mCarros = MutableLiveData<List<Carro>>()
    var carros = mCarros
    private var mCarro = MutableLiveData<Carro>()
    var carro = mCarro
    private var mDelete = MutableLiveData<Boolean>()
    var delete = mDelete


    fun listCarros(){
        val call: Call<List<Carro>> = mRemote.list(mToken)
        serviceCarroList(call)
    }
    private fun serviceCarroList(call: Call<List<Carro>>) {
        call.enqueue(object : Callback<List<Carro>> {
            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                print(t.message)
            }
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                val carros: List<Carro>? = response.body()
                mCarros.value = carros

            }
        })
    }
    fun delete(id : Long?){
        val call: Call<ResponseString> = mRemote.delete(mToken, id)
        call.enqueue(object : Callback<ResponseString> {
            override fun onFailure(call: Call<ResponseString>, t: Throwable) {
            }
            override fun onResponse(call: Call<ResponseString>, response: Response<ResponseString>) {
                mDelete.value = true
            }

        })
    }
    fun save(nome : String, descricao : String, id: Long?){
        val carroPost = carroPost(nome, descricao, id)
        val call: Call<Carro> = serviceVerify(mRemote, carroPost)
        call.enqueue(object : Callback<Carro> {
            override fun onFailure(call: Call<Carro>, t: Throwable) {
            }
            override fun onResponse(call: Call<Carro>, response: Response<Carro>) {
                mCarro.value = response.body()
            }
        })
    }
    private fun serviceVerify(remote: CarroService, carroPost: CarroPost): Call<Carro> =
        if (carroPost.id != null) remote.edit(
            mToken,
            carroPost,
            carroPost.id
        ) else remote.save(mToken, carroPost)

    private fun carroPost(nome: String, descricao: String, id: Long?): CarroPost =
        CarroPost(
            url_foto = null,
            id = id,
            nome = nome,
            descricao = descricao
        )

}