package com.example.klever.scenes.carros

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.klever.adapter.CarroListAdapter
import com.example.klever.base.BaseActivity
import com.example.klever.databinding.ActivityCarrosBinding
import com.example.klever.models.Carro
import kotlinx.android.synthetic.main.activity_carros.*


class CarrosActivity : BaseActivity<ActivityCarrosBinding>(ActivityCarrosBinding::inflate), View.OnClickListener {
    private lateinit var viewModel: CarrosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarrosViewModel::class.java)
        viewModel.carros.observe(this, Observer {
            listCarros(it)
        })
        button_add.setOnClickListener(this)
        viewModel.listCarros()
    }
    private fun listCarros(carros: List<Carro>?) {
        val recyclerView = note_list_recyclerview
        recyclerView.adapter = carros?.let { CarroListAdapter(it, this) }
        val layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, CarroFormActivity::class.java)
        startActivity(intent)
    }

    override fun onCreatedView() = binding.run {
    }
}