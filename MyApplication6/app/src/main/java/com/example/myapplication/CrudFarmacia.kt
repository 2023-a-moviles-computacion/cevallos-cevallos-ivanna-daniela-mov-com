package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrudFarmacia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_farmacia)
        val botonBuscarBBD = findViewById<Button>(R.id.btn_buscar_bdd_p)
        botonBuscarBBD.
        setOnClickListener{
            val id = findViewById<EditText>(R.id.input_id_p)
            val nombre = findViewById<EditText>(R.id.input_nombre_p)
            val fecha = findViewById<EditText>(R.id.input_fecha_p)
            val open = findViewById<EditText>(R.id.input_Open_p)
            val stackValue = findViewById<EditText>(R.id.input_stockValor_p)
            val farmacia = EBaseDeDatos.tablaFarmacia!!
                .consultaFarmaciaPorId(
                    id.text.toString().toInt()
                )
            id.setText(farmacia.id.toString())
            nombre.setText(farmacia.nombre)
            fecha.setText(farmacia.fecha)
            open.setText(farmacia.fecha.toString())
            stackValue.setText(farmacia.stackValue.toString())
        }
        val botonCrearBBD = findViewById<Button>(R.id.btn_crear_bdd_p)
        botonCrearBBD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre_p)
                val fecha = findViewById<EditText>(R.id.input_fecha_p)
                val open = findViewById<EditText>(R.id.input_Open_p)
                val stackValue = findViewById<EditText>(R.id.input_stockValor_p)
                EBaseDeDatos.tablaFarmacia!!.crearFarmacia(
                    nombre.text.toString(),
                    fecha.text.toString(),
                    open.text.toString().toBoolean(),
                    stackValue.text.toString().toDouble()
                )
            }
        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd_p)
        botonActualizarBDD
            .setOnClickListener {
                val id = findViewById<EditText>(R.id.input_id_p)
                val nombre = findViewById<EditText>(R.id.input_nombre_p)
                val fecha = findViewById<EditText>(R.id.input_fecha_p)
                val open = findViewById<EditText>(R.id.input_Open_p)
                val stackValue = findViewById<EditText>(R.id.input_stockValor_p)
                EBaseDeDatos.tablaFarmacia!!.actualizarFarmaciaFormulario(
                    id.text.toString().toInt(),
                    nombre.text.toString(),
                    fecha.text.toString(),
                    open.text.toString().toBoolean(),
                    stackValue.text.toString().toDouble()
                    )
            }
        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd_p)
        botonEliminarBDD
            .setOnClickListener {
                val id = findViewById<EditText>(R.id.input_id_p)
                EBaseDeDatos.tablaFarmacia!!.eliminarFarmaciaFormulario(
                    id.text.toString().toInt()
                )
            }

        }


}
