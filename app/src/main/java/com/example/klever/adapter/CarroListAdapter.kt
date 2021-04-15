package com.example.klever.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.klever.CarroFormActivity
import com.example.klever.R
import com.example.klever.models.Carro
import kotlinx.android.synthetic.main.carro_item.view.*


class CarroListAdapter(private val carros: List<Carro>, private val context: Context) :
    Adapter<CarroListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var title: TextView
        var description: TextView

        init {
            title = itemView.note_item_title
            description = itemView.note_item_description
            imageView = itemView.item_image
            itemView.setOnClickListener { v: View ->
                var position: Int = getAdapterPosition()
                val intent = Intent(context, CarroFormActivity::class.java)
                val carro: Carro = carros.get(position)
                intent.putExtra("carro", carro)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
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
}

