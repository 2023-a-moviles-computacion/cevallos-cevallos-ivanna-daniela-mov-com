package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperMedicina (
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    9
){
    override fun onCreate(db: SQLiteDatabase?) {
        Log.e("EsqliteHelperMEdicina", "onCreate called DE MEDICINAB")
        val scriptSQLCrearTablaMedicina =
            """
            CREATE TABLE MEDICINAB(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nombre VARCHAR(50),
        fecha VARCHAR(50),
        caducado BOOLEAN,
        precio DOUBLE,
        farmaciaId INTEGER
    )
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaMedicina)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS MEDICINAB")

        onCreate(db)
    }

    fun crearMedicina(
        nombre: String,
        fecha: String,
        caducado: Boolean,
        precio:Double,
        farmaciaId: Int
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fecha", fecha)
        valoresAGuardar.put("caducado",caducado)
        valoresAGuardar.put("precio",precio)
        valoresAGuardar.put("farmaciaId",farmaciaId)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "MEDICINAB",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }
    fun eliminarMedicinaFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosCosnultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "MEDICINAB",
                "id=?",
                parametrosCosnultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }
    fun actualizarMedicinaFormulario(
        id: Int,
        nombre: String,
        fecha: String,
        caducado: Boolean,
        precio:Double,
        farmaciaId: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fecha", fecha)
        valoresAActualizar.put("caducado",caducado)
        valoresAActualizar.put("precio",precio)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "MEDICINAB",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt()==-1)false else true
    }
    fun consultaMedicinaPorId(
        id:Int
    ): BMedicina{
        val baseDatosLectura =readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM MEDICINAB WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BMedicina(0,"","",false,0.0,0)
        val arreglo = arrayListOf<BMedicina>()
        if(existeUsuario){
            do{
                val existeUsuario = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fecha = resultadoConsultaLectura.getString(2)
                val caducado = resultadoConsultaLectura.getInt(3) != 0
                val precio = resultadoConsultaLectura.getDouble(4)
                val idFarmacia = resultadoConsultaLectura.getInt(5)

                if(id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.fecha = fecha
                    usuarioEncontrado.caducado = caducado
                    usuarioEncontrado.precio = precio
                    usuarioEncontrado.farmaciaId = idFarmacia
                }
            }while(resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado

    }
    fun obtenerTodasLasMedicinas(): ArrayList<BMedicina> {
        val listaMedicinas = ArrayList<BMedicina>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM MEDICINAB"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fecha = resultadoConsultaLectura.getString(2)
                val caducado = resultadoConsultaLectura.getInt(3) != 0
                val precio = resultadoConsultaLectura.getDouble(4)
                val farmaciaId = resultadoConsultaLectura.getInt(5)
                val medicina = BMedicina(id, nombre, fecha, caducado, precio,farmaciaId)
                listaMedicinas.add(medicina)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaMedicinas
    }
    fun getMedicinaByFarmaciaId(farmaciaId: Int): ArrayList<BMedicina> {
        val listaMedicinas = ArrayList<BMedicina>()
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = "SELECT * FROM MEDICINAB WHERE farmaciaId = ?"
        val parametrosConsultaLectura = arrayOf(farmaciaId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fecha = resultadoConsultaLectura.getString(2)
                val caducado = resultadoConsultaLectura.getInt(3) != 0
                val precio = resultadoConsultaLectura.getDouble(4)
                val farmaciaId = resultadoConsultaLectura.getInt(5)
                val medicina = BMedicina(id, nombre, fecha, caducado, precio, farmaciaId)
                listaMedicinas.add(medicina)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaMedicinas
    }

}