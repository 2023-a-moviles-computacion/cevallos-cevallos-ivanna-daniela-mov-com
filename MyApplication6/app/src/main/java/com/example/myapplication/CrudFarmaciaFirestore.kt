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
import android.widget.Toast
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class CrudFarmaciaFirestore : AppCompatActivity() {
    var idItemSeleccionado = 0
    var query: Query? = null
    val arreglo: ArrayList<BFarmacia> = arrayListOf()
    lateinit var adaptador: ArrayAdapter<BFarmacia>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_farmacia_firestore)

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )

        val listView = findViewById<ListView>(R.id.listaFarmacias)

        listView.adapter =  adaptador


        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        // Crear Datos
        val botoDatosCrear = findViewById<Button>(R.id.btn_crear_bdd_i)
        botoDatosCrear.setOnClickListener { crearDatosPrueba() }
        //Visualizar Todos los Datos
        val botonObtenerTodasBBD = findViewById<Button>(R.id.btn_obtener_todas_bdd_p)
        botonObtenerTodasBBD.setOnClickListener {visualizarDatos(adaptador)}
        //Eliminar un dato


    }

    private fun visualizarDatos( adaptador: ArrayAdapter<BFarmacia>) {
        val db = Firebase.firestore
        val pharmacy= db.collection("pharmacies")

        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        pharmacy
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    arreglo.
                        add(
                            BFarmacia(
                                document.id.toInt(),
                                document.data?.get("nombre") as String?,
                                document.data?.get("fecha") as String?,
                                (document.data?.get("open") as String?).toBoolean(),
                                (document.data?.get("stackValue") as String?)?.toDouble(),
                        )
                    )
                }
                adaptador.notifyDataSetChanged()
                Toast.makeText(this, "Pharmacies are displayed!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {  }
    }

    private fun crearDatosPrueba() {
    val db = Firebase.firestore
        val pharmacies = db.collection("pharmacies")
        val nombre = findViewById<EditText>(R.id.input_nombre_i)
        val fecha = findViewById<EditText>(R.id.input_fecha_i)
        val open = findViewById<EditText>(R.id.input_Open_i)
        val stackValue = findViewById<EditText>(R.id.input_stockValor_i)

        val data1 = hashMapOf(
            "nombre" to nombre.text.toString(),
            "fecha" to fecha.text.toString(),
            "open" to open.text.toString(),
            "stackValue" to stackValue.text.toString(),
        )
        val identificador = (Date().time / 1000).toInt()
        pharmacies
            .document(identificador.toString())
            .set(data1)
            .addOnSuccessListener {
                clearEditText(nombre, fecha, open, stackValue)
                Toast.makeText(this, "Pharmacy was created!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {  }

    }
    fun clearEditText(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }
    fun limpiarArreglo() {
        arreglo.clear()
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
        return when(item.itemId){
            R.id.editar_farmacia -> {
                val selectedItemID = idItemSeleccionado
                val selectedFarmacia = arreglo[selectedItemID]
                val selectedFarmaciaName = selectedFarmacia.nombre
                val selectedFarmaciaId= selectedFarmacia.id
                val intent = Intent(this, EditarFarmaciaFirestore::class.java)
                val textView4 = findViewById<TextView>(R.id.textView4)
                textView4.visibility = View.INVISIBLE
                intent.putExtra("nombre_farmacia", selectedFarmaciaName)
                intent.putExtra("id_farmacia", selectedFarmaciaId)
                startActivity(intent)
                true
            }
            R.id.eliminar_farmacia -> {
                val selectedItemID = idItemSeleccionado
                val selectedFarmacia = arreglo[selectedItemID]
                val selectedFarmaciaId= selectedFarmacia.id
                eliminarRegistro(selectedFarmaciaId)
                true
            }
            R.id.anadir_medicina->{
                val selectedItemID = idItemSeleccionado
                val selectedFarmacia = arreglo[selectedItemID]
                val selectedFarmaciaName = selectedFarmacia.nombre
                val selectedFarmaciaId= selectedFarmacia.id
                val textView4 = findViewById<TextView>(R.id.textView4)
                textView4.visibility = View.INVISIBLE
                val intent = Intent(this, ListViewMedicinaFirestore::class.java)
                intent.putExtra("nombre_farmacia", selectedFarmaciaName)
                intent.putExtra("id_farmacia", selectedFarmaciaId.toString())
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun eliminarRegistro(selectedFarmaciaId:Int) {
        val db = Firebase.firestore
        val refPharmacy = db.collection("pharmacies")
        refPharmacy
            .document(selectedFarmaciaId.toString())
            .delete()
            .addOnSuccessListener {
                adaptador.notifyDataSetChanged()
                Toast.makeText(this, " Pharmacy has been deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {  }
    }


}