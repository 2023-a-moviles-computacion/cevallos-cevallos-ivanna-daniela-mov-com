package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ECrudEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)
        val botonBuscarBBD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBBD.
                setOnClickListener{
                    val id= findViewById<EditText>(R.id.input_id)
                    val nombre = findViewById<EditText>(R.id.input_nombre)
                    val descripcion = findViewById<EditText>(R.id.input_descripcion)
                    val entrenador = EBaseDeDatos.tablaEntrenador!!
                        .consultaEntrenadorPorId(
                            id.text.toString().toInt()
                        )
                    id.setText(entrenador.id.toString())
                    nombre.setText(entrenador.nombre)
                    descripcion.setText(entrenador.descripcion)
                }

    }
}