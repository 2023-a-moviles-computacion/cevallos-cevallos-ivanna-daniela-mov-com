package com.example.myapplication

class BaseDatosLibros {
    companion object {
        val arregloLibros = arrayListOf<Libros>()
        init {
            arregloLibros
                .add(
                    Libros(1,"Immune", R.drawable.inmunebook)
                )
            arregloLibros
                .add(
                    Libros(1,"No digas nada", R.drawable.nodigasnadabook)
                )
            arregloLibros
                .add(
                    Libros(1,"A Little life", R.drawable.alittlelifebook)
                )
            arregloLibros
                .add(
                    Libros(1,"Farmaco", R.drawable.farmacobook)
                )
            arregloLibros
                .add(
                    Libros(2,"On the loose", R.drawable.ontheloosebook)
                )
            arregloLibros
                .add(
                    Libros(2,"nNever Lie", R.drawable.neverliebook)
                )
            arregloLibros
                .add(
                    Libros(2,"The summer ", R.drawable.thesummerbook)
                )
            arregloLibros
                .add(
                    Libros(2,"One day ", R.drawable.onedaybook)
                )
            arregloLibros
                .add(
                    Libros(3,"The essentials", R.drawable.theessentialsbook)
                )
            arregloLibros
                .add(
                    Libros(3,"Nightwing", R.drawable.nightwingbook)
                )
            arregloLibros
                .add(
                    Libros(3,"Batman ", R.drawable.batmanbook)
                )
            arregloLibros
                .add(
                    Libros(3,"Spiderman", R.drawable.spidermanbook)
                )
        }
    }
}