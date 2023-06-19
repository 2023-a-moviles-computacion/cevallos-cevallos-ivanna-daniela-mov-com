package com.example.movilescomp2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida2)
        botonCicloVida
            .setOnClickListener{
                irActividad(AACicloVida::class.java)
            }
        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener{
                irActividad(BListView::class.java)
            }
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this.clase)
        startActivity(intent)
    }
}