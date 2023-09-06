package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityEditarFarmaciaBinding

class EditarFarmacia : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_farmacia)

        val nombreFarmacia = intent.getStringExtra("nombre_farmacia")
        val idFarmacia = intent.getIntExtra("id_farmacia", -1)
        val selectedItemTextView = findViewById<TextView>(R.id.selectedItem)
        selectedItemTextView.text = nombreFarmacia

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd_e)
        botonActualizarBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_e)
            val fecha = findViewById<EditText>(R.id.input_fecha_e)
            val open = findViewById<EditText>(R.id.input_Open_e)
            val stackValue = findViewById<EditText>(R.id.input_stockValor_e)
            EBaseDeDatos.tablaFarmacia!!.actualizarFarmaciaFormulario(
                idFarmacia,
                nombre.text.toString(),
                fecha.text.toString(),
                open.text.toString().toBoolean(),
                stackValue.text.toString().toDouble()
            )
            Toast.makeText(this, "Se ha actualizado con éxito", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListViewFarmacia::class.java)
            startActivity(intent)
            finish()
        }



    }


}