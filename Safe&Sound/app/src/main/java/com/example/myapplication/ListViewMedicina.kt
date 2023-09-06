package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class ListViewMedicina :AppCompatActivity(){
    var idItemSeleccionado = 0
    lateinit var listaMedicinas: ArrayList<BMedicina>
    lateinit var adapter: ArrayAdapter<BMedicina>
    var farmaciaId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_medicina)

        val nombreFarmacia = intent.getStringExtra("nombre_farmacia")
        val nombrefarm = intent.getStringExtra("nombre_farm")

        val farmaciaId=intent.getIntExtra("id_farmacia",0)
        val selectedItemTextView = findViewById<TextView>(R.id.selectedItem_vm)
        selectedItemTextView.text = nombreFarmacia
        if(nombrefarm!= null){
            selectedItemTextView.text = nombrefarm
        }

        val botonCrearBBD = findViewById<Button>(R.id.btn_crear_bdd_m)
        botonCrearBBD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre_m)
                val fecha = findViewById<EditText>(R.id.input_fecha_m)
                val caducado = findViewById<EditText>(R.id.input_Caducado_m)
                val precio = findViewById<EditText>(R.id.input_precio_m)
                EBaseDeDatos.tablaMedicina!!.crearMedicina(
                    nombre.text.toString(),
                    fecha.text.toString(),
                    caducado.text.toString().toBoolean(),
                    precio.text.toString().toDouble(),
                    farmaciaId
                )
            }
        val botonObtenerTodasBBD = findViewById<Button>(R.id.btn_obtener_todas_bdd_m)
        botonObtenerTodasBBD.setOnClickListener {
            listaMedicinas = EBaseDeDatos.tablaMedicina!!.getMedicinaByFarmaciaId(farmaciaId)
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listaMedicinas)
            val listView = findViewById<ListView>(R.id.listaMedicinas)
            listView.adapter = adapter
            registerForContextMenu(listView)

            val textView5 = findViewById<TextView>(R.id.textView5)
            textView5.text = "Nombre     FechaCaducidad   EstaCaduco   Precio"
            textView5.visibility = View.VISIBLE
        }
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menumedicina, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_medicina -> {
                val medicina = listaMedicinas[idItemSeleccionado]
                val nombreMedicina = medicina.nombre
                val idMedicina = medicina.id
                val textView5 = findViewById<TextView>(R.id.textView5)
                textView5.visibility = View.INVISIBLE
                val intent = Intent(this, EditarMedicina::class.java)
                intent.putExtra("nombre_medicina", nombreMedicina)
                intent.putExtra("id_medicina", idMedicina)
                val selectedItem = findViewById<TextView>(R.id.selectedItem_vm)

                intent.putExtra("nombre_farmacia",selectedItem.text)

                startActivity(intent)

                true
            }
            R.id.eliminar_medicina -> {
                val medicina = listaMedicinas[idItemSeleccionado]
                val id = medicina.id

                EBaseDeDatos.tablaMedicina!!.eliminarMedicinaFormulario(id)

                listaMedicinas.removeAt(idItemSeleccionado)
                adapter.notifyDataSetChanged()

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}