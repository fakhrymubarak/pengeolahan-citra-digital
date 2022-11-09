package com.fakhry.pengolahancitra.helpers.pattern_recognition

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlin.math.abs
import kotlin.math.roundToInt

object RgbImageToHsv {
    suspend fun Bitmap.manipulateHsv(usingLibrary: Boolean): Bitmap {
        val newBitmap = this.copy(Bitmap.Config.RGB_565, true)

        // height and width of Image
        val h = newBitmap.height
        val w = newBitmap.width
        for (x in 0 until w) {
            for (y in 0 until h) {
                // get the old pixel and define rgb
                val oldPixel = this.getPixel(x, y)

                // get hsv from rgb
                val hsv =
                    if (usingLibrary) rgbToHsvAndroid(oldPixel.red, oldPixel.green, oldPixel.blue)
                    else rgbToHsv(oldPixel.red, oldPixel.green, oldPixel.blue)


                val newPixel = if (usingLibrary)
                    Color.HSVToColor(hsv)
                else {
                    val rgb = hsvToRgb(hsv[0], hsv[1], hsv[2])
                    Color.rgb(rgb[0], rgb[1], rgb[2])
                }

                //set the old pixel to the new pixel
                newBitmap.setPixel(x, y, newPixel)
            }
        }
        return newBitmap
    }

    fun rgbToHsv(red: Int, green: Int, blue: Int): FloatArray {
        val maxRgb = 255f
        val hsv = FloatArray(3)

        // set R' G' B'
        val tpR = red / maxRgb
        val tpG = green / maxRgb
        val tpB = blue / maxRgb

        // set cMax, cMin, deltaC
        val cMax = tpR.coerceAtLeast(tpG).coerceAtLeast(tpB)
        val cMin = tpR.coerceAtMost(tpG).coerceAtMost(tpB)
        val delta = cMax - cMin

        // calculate hue
        hsv[0] = if (delta == 0f) 0f
        else when (cMax) {
            tpR -> 60 * (((tpG - tpB) / delta) % 6)
            tpG -> 60 * (((tpB - tpR) / delta) + 2)
            tpB -> 60 * (((tpR - tpG) / delta) + 4)
            else -> 0f
        }

        // calculate saturation
        hsv[1] = if (delta == 0f) 0f else delta / cMax

        // calculate vue
        hsv[2] = cMax

        return hsv
    }


    /**
     *
     * @see [https://www.rapidtables.com/convert/color/hsv-to-rgb.html]
     * */
    fun hsvToRgb(hue: Float, saturation: Float, value: Float): IntArray {
        val newHue = if (hue < 0) hue + 360 else hue

        val rgb = IntArray(3)

        // set C, X, M
        val c = value * saturation
        val x = c * (1 - abs(newHue / 60 % 2 - 1))
        val m = value - c

        // calculate R, G, B
        when (newHue) {
            in 0f..59f -> {
                rgb[0] = ((c + m) * 255).roundToInt()
                rgb[1] = ((x + m) * 255).roundToInt()
                rgb[2] = ((0f + m) * 255).roundToInt()
            }
            in 60f..119f -> {
                rgb[0] = ((x + m) * 255).roundToInt()
                rgb[1] = ((c + m) * 255).roundToInt()
                rgb[2] = ((0f + m) * 255).roundToInt()
            }
            in 120f..179f -> {
                rgb[0] = ((0f + m) * 255).roundToInt()
                rgb[1] = ((c + m) * 255).roundToInt()
                rgb[2] = ((x + m) * 255).roundToInt()
            }
            in 180f..239f -> {
                rgb[0] = ((0f + m) * 255).roundToInt()
                rgb[1] = ((x + m) * 255).roundToInt()
                rgb[2] = ((c + m) * 255).roundToInt()
            }
            in 240f..299f -> {
                rgb[0] = ((x + m) * 255).roundToInt()
                rgb[1] = ((0f + m) * 255).roundToInt()
                rgb[2] = ((c + m) * 255).roundToInt()
            }
            in 300f..360f -> {
                rgb[0] = ((c + m) * 255).roundToInt()
                rgb[1] = ((0f + m) * 255).roundToInt()
                rgb[2] = ((x + m) * 255).roundToInt()
            }
            else -> {
                rgb[0] = ((0f + m) * 255).roundToInt()
                rgb[1] = ((0f + m) * 255).roundToInt()
                rgb[2] = ((0f + m) * 255).roundToInt()
            }
        }
        return rgb
    }

    private fun rgbToHsvAndroid(red: Int, green: Int, blue: Int): FloatArray {
        val hsv = FloatArray(3)
        Color.RGBToHSV(red, green, blue, hsv)
        return hsv
    }
}