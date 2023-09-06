package com.example.myapplication

class BMedicina (
    var id: Int,
    var nombre: String?,
    var fecha: String?,
    var caducado: Boolean?,
    var precio: Double?,
    var farmaciaId: Int?
)
{
    override fun toString(): String {
        val formattedNombre = nombre?.padEnd(20) ?: "N/A".padEnd(20)
        val formattedFecha = fecha?.padEnd(15) ?: "N/A".padEnd(15)
        val formattedCaducado = caducado?.toString()?.padEnd(10) ?: "N/A".padEnd(10)
        val formattedPrecio = precio?.toString()?.padEnd(10) ?: "N/A".padEnd(10)


        return "$formattedNombre $formattedFecha $formattedCaducado $formattedPrecio"
    }
}