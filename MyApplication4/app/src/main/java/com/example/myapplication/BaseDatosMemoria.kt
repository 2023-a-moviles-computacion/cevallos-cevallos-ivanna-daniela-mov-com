package com.example.myapplication

class BaseDatosMemoria {
    companion object {
        val arregloCategorias = arrayListOf<Categorias>()
        init {
            arregloCategorias
                .add(
                    Categorias(1, "Explore")
                )
            arregloCategorias
                .add(
                    Categorias(2,"Prime Reading")
                )
            arregloCategorias
                .add(
                    Categorias(3, "Kindle Vella")
                )
            arregloCategorias
                .add(
                    Categorias(3, "Romance")
                )
            arregloCategorias
                .add(
                    Categorias(3, "Mystery, Thiller & Suspense")
                )
            arregloCategorias
                .add(
                    Categorias(3, "Science Fiction & Fantasy")
                )
            arregloCategorias
                .add(
                    Categorias(3, "Literature & Fiction")
                )
            arregloCategorias
                .add(
                    Categorias(3, "Religion & Sprituality")
                )
        }
    }

}