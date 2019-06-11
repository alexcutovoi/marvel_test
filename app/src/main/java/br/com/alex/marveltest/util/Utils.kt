package br.com.alex.marveltest.util

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream

class Utils {
    companion object{
        fun convertBitmapToByteArray(bitmapDrawable: BitmapDrawable): ByteArray{
            val bitmap: Bitmap = bitmapDrawable.bitmap
            val byteStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
            return byteStream.toByteArray()
        }
    }
}