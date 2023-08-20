package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdaptadorLibros(
    private val contexto: Home,
    private val lista: ArrayList<Libros>,
    private val recyclerView: RecyclerView,
    private val targetId: Int // Change the parameter name to targetId
): RecyclerView.Adapter<RecyclerViewAdaptadorLibros.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageBookView: ImageView

        init {
            imageBookView = view.findViewById(R.id.iv_libros)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdaptadorLibros.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_libros,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        // Count the number of items with matching targetId
        return lista.count { it.id == targetId }
    }

    override fun onBindViewHolder(holder: RecyclerViewAdaptadorLibros.MyViewHolder, position: Int) {
        val matchingItems = lista.filter { it.id == targetId }
        val catActual = matchingItems[position]
        holder.imageBookView.setImageResource(catActual.imageId!!)
    }
}
