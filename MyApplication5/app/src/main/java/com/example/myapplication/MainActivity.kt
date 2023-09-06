package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonAgendar = findViewById<Button>(R.id.btn_agendar)
        botonAgendar.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val displayEmail = user?.email
            if (!displayEmail.isNullOrEmpty()) {
                irActividad(SelectCategory::class.java)
            }
            else{
                irActividad(login::class.java)
            }
            }
        val botonFirestoreDoctorCategory = findViewById<Button>(R.id.btn_crear_base)
        botonFirestoreDoctorCategory.setOnClickListener {
            irActividad(Firestore_Doctor_Category_Appointmenet::class.java)
        }
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}