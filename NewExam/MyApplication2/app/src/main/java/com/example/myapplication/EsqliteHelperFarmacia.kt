package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class EsqliteHelperFarmacia(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    9
){
    override fun onCreate(db: SQLiteDatabase?) {
        Log.e("EsqliteHelperFarmacia", "onCreate called")
        val scriptSQLCrearTablaFarmacia =
            """
            CREATE TABLE FARMACIA(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            fecha VARCHAR(50),
            open BOOLEAN,
            stackvalue DOUBLE
        )
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaFarmacia)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS FARMACIA")
        onCreate(db)
    }
    fun crearFarmacia(
        nombre: String,
        fecha: String,
        open: Boolean,
        stackValue:Double,
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fecha", fecha)
        valoresAGuardar.put("open",open)
        valoresAGuardar.put("stackValue",stackValue)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "FARMACIA",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }
    fun eliminarFarmaciaFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosCosnultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "FARMACIA",
                "id=?",
                parametrosCosnultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }
    fun actualizarFarmaciaFormulario(
        id: Int,
        nombre: String,
        fecha: String,
        open: Boolean,
        stackValue:Double
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fecha", fecha)
        valoresAActualizar.put("open",open)
        valoresAActualizar.put("stackValue",stackValue)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "FARMACIA",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt()==-1)false else true
    }
    fun consultaFarmaciaPorId(
        id:Int
    ): BFarmacia{
        val baseDatosLectura =readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM FARMACIA WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BFarmacia(0,"","",false,0.0)
        val arreglo = arrayListOf<BFarmacia>()
        if(existeUsuario){
            do{
                val existeUsuario = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fecha = resultadoConsultaLectura.getString(2)
                val open = resultadoConsultaLectura.getInt(3) != 0
                val stackValue = resultadoConsultaLectura.getDouble(4)

                if(id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.fecha = fecha
                    usuarioEncontrado.open = open
                    usuarioEncontrado.stackValue = stackValue
                }
            }while(resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado

    }
    fun obtenerTodasLasFarmacias(): ArrayList<BFarmacia> {
        val listaFarmacias = ArrayList<BFarmacia>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM FARMACIA"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fecha = resultadoConsultaLectura.getString(2)
                val open = resultadoConsultaLectura.getInt(3) != 0
                val stackValue = resultadoConsultaLectura.getDouble(4)


                val farmacia = BFarmacia(id, nombre, fecha, open, stackValue)
                listaFarmacias.add(farmacia)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaFarmacias
    }

}