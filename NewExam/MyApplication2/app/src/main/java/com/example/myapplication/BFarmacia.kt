package com.example.myapplication

class BFarmacia(
    var id: Int,
    var nombre: String?,
    var fecha: String?,
    var open: Boolean?,
    var stackValue: Double?
)
{
    override fun toString(): String {
        val padding = " ".repeat(10) // Adjust the padding length as needed

        val columnWidth = 7 // Width for columns other than the first column
        val firstColumnWidth = 11 // Width for the first column

        val formattedNombre = nombre?.padEnd(firstColumnWidth) ?: "N/A".padEnd(firstColumnWidth)
        val formattedFecha = fecha?.padEnd(firstColumnWidth) ?: "N/A".padEnd(firstColumnWidth)
        val formattedOpen = open?.toString()?.padEnd(columnWidth) ?: "N/A".padEnd(columnWidth)
        val formattedStackValue = stackValue?.toString()?.padEnd(columnWidth) ?: "N/A".padEnd(columnWidth)

        return "$formattedNombre $formattedFecha $formattedOpen $formattedStackValue"

    }


}