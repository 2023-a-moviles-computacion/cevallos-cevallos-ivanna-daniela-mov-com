package com.example.myapplication

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class Libros (var id: Int?,
              var nombre: String?, var imageId: Int?) {

    fun getImageDrawable(context: Context): Drawable? {
        return ContextCompat.getDrawable(context, imageId!!)
    }
}