package com.example.myapplication

class Appointment (
    var id: String?,
    var date: String?,
    var hour: String?,
    var idPaciente: String?,
    var idMedico: String?,

){
    constructor() : this(null, null, null, null, null)
}