package com.example.klever

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.klever.infra.CallService
import com.example.klever.infra.RetrofitClient
import com.example.klever.infra.Token
import com.example.klever.models.Carro
import com.example.klever.models.CarroPost
import com.example.klever.models.ResponseString
import com.example.klever.services.CarroService
import kotlinx.android.synthetic.main.activity_carro_form.*
import retrofit2.Call


class CarroFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var token: String
    private var carro: Carro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_form)
        token = "Bearer ${Token.createToken("")}"
        carro = intent.getSerializableExtra("carro") as Carro?
        popularValorEdit()
        buttonSalvar.setOnClickListener(this)
        buttonDeletar.setOnClickListener(this)
    }

    private fun popularValorEdit() {
        if (carro != null) {
            nome_carro.setText(carro?.nome)
            descricao_carro.setText(carro?.descricao)
        } else {
            buttonDeletar.visibility = View.INVISIBLE

        }
    }

    override fun onClick(v: View?) {
        val remote = RetrofitClient.createService(CarroService::class.java)
        val builder = AlertDialog.Builder(this)
        if (v?.id == R.id.buttonDeletar) {
            criarDialog(builder, "O carro foi Deletado com Sucesso!")
            val call: Call<ResponseString> = remote.delete(token, carro?.id)
            CallService.serviceResponseString(call, builder)
        } else {
            criarDialog(builder, "O carro foi Salvo com Sucesso!")
            val nome = nome_carro.text.toString()
            val descricao = descricao_carro.text.toString()
            val carroPost = carroPost(nome, descricao, carro?.id)
            val call: Call<Carro> = serviceVerify(remote, carroPost)
            CallService.serviceCarro(call, builder)
        }

    }

    private fun serviceVerify(remote: CarroService, carroPost: CarroPost): Call<Carro> =
         if (carroPost.id != null) remote.edit(
            token,
            carroPost,
            carroPost.id
        ) else remote.save(token, carroPost)

    private fun carroPost(nome: String, descricao: String, id: Long?): CarroPost =
         CarroPost(
            url_foto = null,
            id = id,
            nome = nome,
            descricao = descricao
        )

    private fun criarDialog(builder: AlertDialog.Builder, mensagem: String) {
        builder.setTitle("Alerta")
        builder.setMessage(mensagem)
        val intent = Intent(this, CarrosActivity::class.java)
        builder.setPositiveButton("OK") { dialog, which ->
            startActivity(intent)
        }
    }
}