package com.example.klever.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.klever.CarroFormActivity
import com.example.klever.R
import com.example.klever.models.Carro
import kotlinx.android.synthetic.main.carro_item.view.*
import java.io.InputStream
import java.net.URL


class CarroListAdapter(private val carros: List<Carro>, private val context: Context) : Adapter<CarroListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var title: TextView
        var description: TextView

        init {
             title = itemView.note_item_title
             description = itemView.note_item_description
             imageView = itemView.item_image
            itemView.setOnClickListener { v: View  ->
                var position: Int = getAdapterPosition()
                val intent =  Intent(context, CarroFormActivity::class.java)
                val carro : Carro = carros.get(position)
                intent.putExtra("carro",carro)
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
            DownloadImageFromInternet(it.imageView).execute(carro.urlFoto)
        }
    }

    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(context, "Carregando os dados...",     Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            var imageURL = urls[0]
            if(imageURL==null){
                imageURL = "https://images-americanas.b2w.io/produtos/01/00/img/1371595/4/1371595496_1GG.jpg";
            }
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}

