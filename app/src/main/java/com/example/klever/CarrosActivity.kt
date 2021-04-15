package com.example.klever

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.klever.adapter.CarroListAdapter
import com.example.klever.infra.RetrofitClient
import com.example.klever.models.Carro
import com.example.klever.services.CarroService
import kotlinx.android.synthetic.main.activity_carros.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CarrosActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var shared: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        shared = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = shared.getString("token", "")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)
        button_add.setOnClickListener(this)
        val remote = RetrofitClient.createService(CarroService::class.java)
        val call: Call<List<Carro>> = remote.list("Bearer " + token)
        serviceCarroList(call)
    }

    private fun serviceCarroList(call: Call<List<Carro>>) {
        call.enqueue(object : Callback<List<Carro>> {
            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                val carros: List<Carro>? = response.body()
                listCarros(carros)
            }


        })
    }

    private fun listCarros(carros: List<Carro>?) {
        val recyclerView = note_list_recyclerview
        recyclerView.adapter = carros?.let { CarroListAdapter(it, applicationContext) }
        val layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, CarroFormActivity::class.java)
        startActivity(intent)
    }
}