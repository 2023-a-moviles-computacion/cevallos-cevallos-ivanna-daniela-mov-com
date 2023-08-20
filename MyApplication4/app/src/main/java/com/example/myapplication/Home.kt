package com.example.myapplication

import CustomItemDecoration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        inicializarRecyclerView()
        inicializarRecyclerViewB(1, R.id.rv_library)
        inicializarRecyclerViewB(2, R.id.rv_library2)
        inicializarRecyclerViewB(3, R.id.rv_library3)
    }

    fun inicializarRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_categoria)
        val adapatador = RecyclerViewAdaptadorCategoria(
            this,
            BaseDatosMemoria.arregloCategorias,
            recyclerView
        )
        recyclerView.adapter = adapatador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapatador.notifyDataSetChanged()
    }
    fun inicializarRecyclerViewB(number : Int, recyclerViewId: Int) {
        val recyclerView = findViewById<RecyclerView>(recyclerViewId)
        val adapatador = RecyclerViewAdaptadorLibros(
            this,
            BaseDatosLibros.arregloLibros,
            recyclerView,
            number
        )
        recyclerView.adapter = adapatador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapatador.notifyDataSetChanged()
    }
}
