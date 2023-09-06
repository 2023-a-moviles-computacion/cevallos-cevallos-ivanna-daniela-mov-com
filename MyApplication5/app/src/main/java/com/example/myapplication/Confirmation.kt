package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Confirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
        val nameDoctor = intent.getStringExtra("nameDoctor")
        val date = intent.getStringExtra("date")
        val hour = intent.getStringExtra("hour")

        val txtConfirmation = findViewById<TextView>(R.id.txtConfirmation)
        txtConfirmation.text = "You have scheduled an appointment with $nameDoctor on $date at $hour"

        val btnAppointments = findViewById<Button>(R.id.btnconfirmation_appointment)
        btnAppointments.setOnClickListener {
            irActividad(UsersAppointments::class.java)
        }


    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}