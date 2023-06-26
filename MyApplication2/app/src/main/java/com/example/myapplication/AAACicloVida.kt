package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class AAACicloVida : AppCompatActivity() {



    var textoGlobal = ""

    fun mostrarSnackbar(texto:String){
        textoGlobal += texto
        Snackbar.make(findViewById(R.id.cl_ciclo_vida),
            textoGlobal, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aaaciclo_vida)
        mostrarSnackbar("OnCreate")
    }
    override fun onStart() {
        super.onStart()
        mostrarSnackbar( "onStart")
    }
    override fun onResume() {
        super.onResume()
        mostrarSnackbar( "onResume")
    }
    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar( "onRestart")
    }
    override fun onPause() {
        super.onPause()
        mostrarSnackbar("onPause")
    }
    override fun onStop() {
        super.onStop()
        mostrarSnackbar( "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar( "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // GUARDAR LAS VARIABLES
            // PRIMITIVOS
            putString("textoGuardado", textoGlobal)
            //putInt("numeroGuardado", numero)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // RECUPERAR LAS VARIABLES
        // PRIMITIVOS
        val textoRecuperado:String? = savedInstanceState.getString("textoGuardado")
        // val textoRecuperado:Int? = savedInstanceState.getInt("numeroGuardado")
        if(textoRecuperado!= null){
            mostrarSnackbar(textoRecuperado)
            textoGlobal = textoRecuperado
        }
    }
}