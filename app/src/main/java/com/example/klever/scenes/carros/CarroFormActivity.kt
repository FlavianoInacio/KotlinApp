package com.example.klever.scenes.carros

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.klever.R
import com.example.klever.base.BaseActivity
import com.example.klever.databinding.ActivityCarroFormBinding
import com.example.klever.models.Carro
import kotlinx.android.synthetic.main.activity_carro_form.*


class CarroFormActivity : BaseActivity<ActivityCarroFormBinding>(ActivityCarroFormBinding::inflate), View.OnClickListener {
    private var carro: Carro? = null
    private lateinit var viewModel: CarrosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarrosViewModel::class.java)
        carro = intent.getSerializableExtra("carro") as Carro?
        popularValorEdit()
        buttonSalvar.setOnClickListener(this)
        buttonDeletar.setOnClickListener(this)
        viewModel.carro.observe(this, Observer {
            criarDialog("Carro Salvo com Sucesso!")
        })
        viewModel.delete.observe(this, Observer {
            criarDialog("Carro Deletado com Sucesso!")
        })
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
        if (v?.id == R.id.buttonDeletar) {
            viewModel.delete(carro?.id)
        } else {
            val nome = nome_carro.text.toString()
            val descricao = descricao_carro.text.toString()
            viewModel.save(nome,descricao,carro?.id)
        }
    }
    private fun criarDialog(mensagem: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alerta")
        builder.setMessage(mensagem)
        val intent = Intent(this, CarrosActivity::class.java)
        builder.setPositiveButton("OK") { dialog, which ->
            startActivity(intent)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    override fun onCreatedView() = binding.run {
    }
}