package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityFrecycleViewBinding

class FRecycleView : AppCompatActivity() {
var totalLikes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_frecycle_view)
        super.onCreate(savedInstanceState)
        inicializarRecyclerView()
    }
    fun aumentarTotalLikes(){
        totalLikes = totalLikes+1
        val totalLikesTextView = findViewById<TextView>(R.id.tv_total_likes)
        totalLikesTextView.text = totalLikes.toString()
    }
    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_entrenadores)
        val adapatador = FRecyclerViewAdaptadorNombreDescripcion(
            this,
            BBaseDatosMemoria.arregloBEntrenador,
            recyclerView
        )
        recyclerView.adapter = adapatador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adapatador.notifyDataSetChanged()
    }


}