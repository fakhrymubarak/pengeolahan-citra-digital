package com.example.pengolahancitra.helpers.pattern_recognition

import android.graphics.Bitmap

object RgbImageToHsv {
    fun Bitmap.rgbToHsv(): Bitmap {
        val newBitmap = this.copy(Bitmap.Config.RGB_565, true)

        // height and width of Image
        val h = newBitmap.height
        val w = newBitmap.width
        for (x in 0 until w) {
            for (y in 0 until h) {
                //get the old pixel
                val oldPixel = this.getPixel(x, y)

                //set the old pixel to the new pixel
                newBitmap.setPixel(((w - 1) - x), y, oldPixel)
            }
        }
        return newBitmap
    }
}