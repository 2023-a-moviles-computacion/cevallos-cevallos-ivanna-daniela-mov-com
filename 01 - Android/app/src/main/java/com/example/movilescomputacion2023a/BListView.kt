package com.example.movilescomputacion2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    var idItemSeleccionado =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAnadirListView = findViewById<Button>(
            R.id.btn_anadir_list_view)
        botonAnadirListView
            .setOnClickListener{
                anadirEntrenador(adaptador)
            }
    }
    fun anadirEntrenador(
        adpatador : ArrayAdapter<BEntrenador>
    ){
        arreglo.add(
            BEntrenador(
                1,
                "Andrian",
                "Descripcion"
            )
        )
        adaptador.notifyDataSetChanged()
    }
}