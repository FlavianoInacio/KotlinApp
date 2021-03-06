package com.example.klever.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.klever.scenes.carros.CarroFormActivity
import com.example.klever.R
import com.example.klever.models.Carro
import com.example.klever.listener.CarroListener
import kotlinx.android.synthetic.main.carro_item.view.*


class CarroListAdapter(private val carros: List<Carro>, private val context: Context, private val listener: CarroListener) :Adapter<CarroListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // obtem o layout do adapter carro_item
        val itemView = LayoutInflater.from(context).inflate(R.layout.carro_item, parent, false)
        // retorna viewHolder com os itens da view
        return ViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        // retorna o tamanho da lista.
        // a lista carros.size é iniciada no construtor quando chamada pela activity carrosActivity
        return carros.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carro = carros[position]
        holder?.let {
            it.title.text = carro.nome
            it.description.text = carro.descricao
            val imgUrl: String = carro.urlFoto
            Glide.with(this.context)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(it.imageView);
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.item_image
        val title = itemView.note_item_title
        val description = itemView.note_item_description

        init {
            itemView.setOnClickListener { v: View ->
                val position = adapterPosition
                val intent = Intent(context, CarroFormActivity::class.java)
                val carro: Carro = carros[position]
                intent.putExtra("carro", carro)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent)
            }
            
            itemView.setOnLongClickListener { v: View ->
                AlertDialog.Builder(itemView.context)
                    .setTitle("Alerta")
                    .setMessage("Deseja remover esse carro?")
                    .setPositiveButton("Remover"){ dialog, which ->
                        listener.onDelete(carros[adapterPosition].id)
                    }
                    .setNeutralButton("Cancelar",null)
                    .show()
                true
            }
        }

    }
}

