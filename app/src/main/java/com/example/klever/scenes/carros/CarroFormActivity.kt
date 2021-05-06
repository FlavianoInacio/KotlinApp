package com.example.klever.scenes.carros

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.klever.base.BaseActivity
import com.example.klever.databinding.ActivityCarroFormBinding
import com.example.klever.infra.ProcessResult
import com.example.klever.models.Carro
import kotlinx.android.synthetic.main.activity_carro_form.*


class CarroFormActivity : BaseActivity<ActivityCarroFormBinding>(ActivityCarroFormBinding::inflate),
    View.OnClickListener {
    private var carro: Carro? = null
    private lateinit var viewModel: CarrosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarrosViewModel::class.java)
        carro = intent.getSerializableExtra("carro") as Carro?
        popularValorEdit()
        buttonSalvar.setOnClickListener(this)
    }

    private fun popularValorEdit() {
        if (carro != null) {
            nome_carro.setText(carro?.nome)
            descricao_carro.setText(carro?.descricao)
        }
    }

    override fun onClick(v: View?) {
        val nome = nome_carro.text.toString()
        val descricao = descricao_carro.text.toString()
        viewModel.save(nome, descricao, carro?.id).observe(this, Observer {
            when (it) {
                ProcessResult.SUCESS -> {
                    super.onBackPressed();
                }
                ProcessResult.ERROR -> {
                    Toast.makeText(applicationContext, "Algo deu errado!", Toast.LENGTH_LONG)
                }
            }
        })
    }

    override fun onCreatedView() = binding.run {
    }
}