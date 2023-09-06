package com.example.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import com.example.myapplication.BFarmacia
import com.example.myapplication.EBaseDeDatos
import com.example.myapplication.R

class ListViewFarmacia : AppCompatActivity() {
    var idItemSeleccionado = 0
    lateinit var listaFarmacias: ArrayList<BFarmacia>
    lateinit var adapter: ArrayAdapter<BFarmacia>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_farmacia)
        val botonCrearBBD = findViewById<Button>(R.id.btn_crear_bdd_i)
        botonCrearBBD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre_i)
                val fecha = findViewById<EditText>(R.id.input_fecha_i)
                val open = findViewById<EditText>(R.id.input_Open_i)
                val stackValue = findViewById<EditText>(R.id.input_stockValor_i)
                EBaseDeDatos.tablaFarmacia!!.crearFarmacia(
                    nombre.text.toString(),
                    fecha.text.toString(),
                    open.text.toString().toBoolean(),
                    stackValue.text.toString().toDouble()
                )
            }
        val botonObtenerTodasBBD = findViewById<Button>(R.id.btn_obtener_todas_bdd_p)
        botonObtenerTodasBBD.setOnClickListener {
            listaFarmacias = EBaseDeDatos.tablaFarmacia!!.obtenerTodasLasFarmacias()

            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaFarmacias)
            val listView = findViewById<ListView>(R.id.listaFarmacias)
            listView.adapter = adapter
            registerForContextMenu(listView)

            val textView4 = findViewById<TextView>(R.id.textView4)
            textView4.text = "Nombre     FechaApertura   ContinuaAbierto   ValorStock"
            textView4.visibility = View.VISIBLE

        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menufarmacia, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_farmacia -> {
                val farmacia = listaFarmacias[idItemSeleccionado]
                val nombreFarmacia = farmacia.nombre
                val idFarmacia = farmacia.id
                val textView4 = findViewById<TextView>(R.id.textView4)
                textView4.visibility = View.INVISIBLE
                val intent = Intent(this, EditarFarmacia::class.java)
                intent.putExtra("nombre_farmacia", nombreFarmacia)
                intent.putExtra("id_farmacia", idFarmacia)

                startActivity(intent)

                true
            }
            R.id.eliminar_farmacia -> {
                val farmacia = listaFarmacias[idItemSeleccionado]
                val id = farmacia.id

                EBaseDeDatos.tablaFarmacia!!.eliminarFarmaciaFormulario(id)

                listaFarmacias.removeAt(idItemSeleccionado)
                adapter.notifyDataSetChanged()

                true
            }
            R.id.anadir_medicina->{
                val farmacia = listaFarmacias[idItemSeleccionado]
                val nombreFarmacia = farmacia.nombre
                val idFarmacia = farmacia.id
                val textView4 = findViewById<TextView>(R.id.textView4)
                textView4.visibility = View.INVISIBLE
                val intent = Intent(this, ListViewMedicina::class.java)
                intent.putExtra("nombre_farmacia", nombreFarmacia)
                intent.putExtra("id_farmacia", idFarmacia)
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
