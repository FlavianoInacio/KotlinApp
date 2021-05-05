package com.example.klever.scenes.carros

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.klever.adapter.CarroListAdapter
import com.example.klever.base.BaseActivity
import com.example.klever.databinding.ActivityCarrosBinding
import com.example.klever.infra.ProcessResult
import com.example.klever.models.Carro
import com.example.klever.scenes.listener.CarroListener
import kotlinx.android.synthetic.main.activity_carros.*


class CarrosActivity : BaseActivity<ActivityCarrosBinding>(ActivityCarrosBinding::inflate),
    View.OnClickListener {
    private lateinit var mViewModel: CarrosViewModel
    private lateinit var mListener: CarroListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(CarrosViewModel::class.java)
        button_add.setOnClickListener(this)
        listener()
    }
    private fun listener() {
        mListener = object : CarroListener {
            override fun onDelete(id: Long) {
                mViewModel.delete(id).observe(this@CarrosActivity, Observer {
                    when (it) {
                        ProcessResult.SUCESSO -> {
                            onResume()
                        }
                        ProcessResult.ERROR -> {
                            Toast.makeText(
                                applicationContext,
                                "Algo deu errado!",
                                Toast.LENGTH_LONG
                            )
                        }
                    }
                })
            }
        }
    }

    private fun inflateAdapter(carros: List<Carro>?) {
        // obtem reciclyview
        val recyclerView = note_list_recyclerview
        // define o adapter
        recyclerView.adapter = carros?.let { CarroListAdapter(it, this, listener = mListener) }
        // define o layout
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()
        mViewModel.listCarros().observe(this, Observer {
            inflateAdapter(it)
        })
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, CarroFormActivity::class.java)
        startActivity(intent)
    }

    override fun onCreatedView() = binding.run {
    }
}