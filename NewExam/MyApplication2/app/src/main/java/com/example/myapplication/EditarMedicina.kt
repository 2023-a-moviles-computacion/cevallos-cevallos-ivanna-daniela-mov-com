package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat

class EditarMedicina : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_medicina)

    val nombreMedicina = intent.getStringExtra("nombre_medicina")
    val idMedicina = intent.getIntExtra("id_medicina", -1)
        val idFarmacia = intent.getIntExtra("id_farmacia", -1)
        val nombreFarm = intent.getStringExtra("nombre_farmacia")
    val selectedItemTextView = findViewById<TextView>(R.id.selectedItem_em)
    selectedItemTextView.text = nombreMedicina

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd_em)
        botonActualizarBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_em)
            val fecha = findViewById<EditText>(R.id.input_fecha_em)
            val caducado = findViewById<EditText>(R.id.input_caducado_em)
            val precio = findViewById<EditText>(R.id.input_precio_em)
            EBaseDeDatos.tablaMedicina!!.actualizarMedicinaFormulario(
                idMedicina,
                nombre.text.toString(),
                fecha.text.toString(),
                caducado.text.toString().toBoolean(),
                precio.text.toString().toDouble(),
                idFarmacia
            )
            Toast.makeText(this, "Se ha actualizado con éxito", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListViewMedicina::class.java)
            intent.putExtra("nombre_farm", nombreFarm)
            startActivity(intent)
            finish()
        }

    }
}