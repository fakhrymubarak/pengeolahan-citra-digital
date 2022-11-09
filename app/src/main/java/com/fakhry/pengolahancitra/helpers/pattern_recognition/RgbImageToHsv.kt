package com.fakhry.pengolahancitra.helpers.pattern_recognition

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

object RgbImageToHsv {
    fun Bitmap.manipulateHsv(
        hue: Double,
        saturation: Double,
        value: Double,
        usingLibrary: Boolean
    ): Bitmap {
        val newBitmap = this.copy(Bitmap.Config.RGB_565, true)

        // height and width of Image
        val h = newBitmap.height
        val w = newBitmap.width
        for (x in 0 until w) {
            for (y in 0 until h) {
                // get the old pixel and define rgb
                val oldPixel = this.getPixel(x, y)

                // get hsv from rgb
                val hsv = if (usingLibrary)
                    rgbToHsvAndroid(oldPixel.red, oldPixel.green, oldPixel.blue)
                else
                    rgbToHsv(oldPixel.red, oldPixel.green, oldPixel.blue)

                val newHue = hsv[0] * hue
                val newSaturation = hsv[1] * saturation
                val newValue = hsv[2] * value

                // replace new hsv
                hsv[0] = newHue.toFloat()
                hsv[1] = newSaturation.toFloat()
                hsv[2] = newValue.toFloat()

                val newPixel = Color.HSVToColor(hsv)
                //set the old pixel to the new pixel
                newBitmap.setPixel(x, y, newPixel)
            }
        }
        return newBitmap
    }

    private fun rgbToHsvAndroid(red: Int, green: Int, blue: Int): FloatArray {
        val hsv = FloatArray(3)
        Color.RGBToHSV(red, green, blue, hsv)
        return hsv
    }

    private fun rgbToHsv(red: Int, green: Int, blue: Int): FloatArray {
        val maxRgb = 255f
        val hsv = FloatArray(3)

        // set R' G' B'
        val tpR = red / maxRgb
        val tpG = green / maxRgb
        val tpB = blue / maxRgb

        // set Cmax, Cmin, deltaC
        val cMax = tpR.coerceAtLeast(tpG).coerceAtLeast(tpB)
        val cmin = tpR.coerceAtMost(tpG).coerceAtMost(tpB)
        val delta = cMax - cmin

        // calculate hue
        hsv[0] = if (delta == 0f) 0f
        else when (cMax) {
            tpR -> {
                60 * (((tpG - tpB) / delta) % 6)
            }
            tpB -> {
                60 * (((tpB - tpR) / delta) + 2)
            }
            tpB -> {
                60 * (((tpR - tpG) / delta) + 4)
            }
            else -> {
                0f
            }
        }


        // calculate saturation
        hsv[1] = if (delta == 0f) 0f else delta / cMax

        // calculate vue
        hsv[2] = cMax

        return hsv
    }
}