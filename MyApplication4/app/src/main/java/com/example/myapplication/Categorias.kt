package com.example.myapplication

class Categorias(var id: Int,
                 var nombre: String?) {
    override fun toString():String{
        return "${nombre}"
    }
}