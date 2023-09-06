package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarMedicinaFirestore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_medicina_firestore)
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

            val updatedData = hashMapOf(
                "nombre" to nombre.text.toString(),
                "fecha" to fecha.text.toString(),
                "caducado" to caducado.text.toString(),
                "precio" to precio.text.toString(),
                "farmacia" to idFarmacia.toString()
            )
            actualizarRegistro(idMedicina, updatedData)
            val intent = Intent(this, ListViewMedicinaFirestore::class.java)
            intent.putExtra("nombre_farm", nombreFarm)
            startActivity(intent)
            finish()
        }
    }

    private fun actualizarRegistro(idMedicina: Int, updatedData: HashMap<String, String>) {
        val db = Firebase.firestore
        val refMedicine = db.collection("medicines")
        refMedicine
            .document(idMedicina.toString())
            .update(updatedData as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, "Medicine has been updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {  }

    }
}