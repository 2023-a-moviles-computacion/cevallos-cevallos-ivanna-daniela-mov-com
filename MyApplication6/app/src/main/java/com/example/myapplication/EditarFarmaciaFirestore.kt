package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarFarmaciaFirestore : AppCompatActivity() {
    var query: Query? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_farmacia_firestore)

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

        val updatedData = hashMapOf(
            "nombre" to nombre.text.toString(),
            "fecha" to fecha.text.toString(),
            "open" to open.text.toString(),
            "stackValue" to stackValue.text.toString(),
        )
        actualizarRegistro(idFarmacia, updatedData)
            val intent = Intent(this, CrudFarmaciaFirestore::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun actualizarRegistro(idFarmacia: Int, updatedData: HashMap<String, String>) {
        val db = Firebase.firestore
        val refPharmacy = db.collection("pharmacies")

        refPharmacy
            .document(idFarmacia.toString())
            .update(updatedData as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, " Pharmacy has been updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {  }
    }
}