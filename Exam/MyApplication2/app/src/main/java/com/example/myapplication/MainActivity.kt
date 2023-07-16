package com.example.myapplication


import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    "${data?.getStringExtra("nombreModificado")}"
                }
            }
        }
    val callbackIntentPickUri=
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result->
            if(result.resultCode === RESULT_OK){
                if(result.data != null){
                    val uri : Uri = result.data!!.data!!
                    val cursor = contentResolver.query(uri, null, null, null, null, null)
                    cursor?.moveToFirst()
                    val indiceTelefono = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val telefono = cursor?.getString(indiceTelefono!!)
                    cursor?.close()
                    "Telefono${telefono}"
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Base de datos sqlite
        EBaseDeDatos.tablaEntrenador =  ESqliteHelperEntrenador(this)
        EBaseDeDatos.tablaFarmacia= EsqliteHelperFarmacia(this)
        EBaseDeDatos.tablaMedicina = ESqliteHelperMedicina(this)



        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener {
                irActividad(BListView::class.java)
            }
        val botonIntentImplicito = findViewById<Button>(R.id.btn_sqlite)
        botonIntentImplicito
            .setOnClickListener{
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackIntentPickUri.launch(intentConRespuesta)
            }

        val botonSqlite = findViewById<Button>(R.id.btn_sqlite)
        botonSqlite.setOnClickListener {
            irActividad(ECrudEntrenador::class.java)
        }

        val botonSqlFarmacia = findViewById<Button>(R.id.btn_sql_farmacia)
        botonSqlFarmacia.setOnClickListener {
            irActividad(CrudFarmacia::class.java)
        }
        val botonListViewFarmacia = findViewById<Button>(R.id.btn_list_view_farmacia)
        botonListViewFarmacia.setOnClickListener {
            irActividad(ListViewFarmacia::class.java)
        }
    }
    fun abrirArctividadConParametros(
        clase : Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombre","Adrian")
        intentExplicito.putExtra("apellido", "Eguez")
        intentExplicito.putExtra("adad", 34)
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}