package com.example.klever.infra

import android.app.AlertDialog
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.klever.CarrosActivity
import com.example.klever.R
import com.example.klever.adapter.CarroListAdapter
import com.example.klever.models.Carro
import com.example.klever.models.ResponseString
import kotlinx.android.synthetic.main.activity_carros.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallService{
    companion  object {
         fun serviceCarro(call: Call<Carro>, builder: AlertDialog.Builder) {
            call.enqueue(object : Callback<Carro> {
                override fun onFailure(call: Call<Carro>, t: Throwable) {
                    TODO("Not yet implemented")
                }
                override fun onResponse(call: Call<Carro>, response: Response<Carro>) {
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }

            })
        }
        fun serviceResponseString(call: Call<ResponseString>, builder: AlertDialog.Builder) {
            call.enqueue(object : Callback<ResponseString> {
                override fun onFailure(call: Call<ResponseString>, t: Throwable) {
                    TODO("Not yet implemented")
                }
                override fun onResponse(call: Call<ResponseString>, response: Response<ResponseString>) {
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }

            })
        }
    }
}