package com.fakhry.pengolahancitra.helpers.image_processing

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * Created by Fakhry on 28/05/2021.
 */
object ImageFilters {

    private const val HIGHEST_COLOR_VALUE = 255
    private const val LOWEST_COLOR_VALUE = 0

    /**
     * Apply Grey Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    fun setGreyFilter(oldBitmap: Bitmap): Bitmap {
        // copying to newBitmap for manipulation
        val newBitmap = oldBitmap.copy(Bitmap.Config.RGB_565, true)

        // height and width of Image
        val h = newBitmap.height
        val w = newBitmap.width

        // traversing each pixel in Image as an 2D Array
        for (i in 0 until w) {
            for (j in 0 until h) {

                // getting each pixel
                val oldPixel = oldBitmap.getPixel(i, j)

                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                val oldRed = Color.red(oldPixel)
                val oldBlue = Color.blue(oldPixel)
                val oldGreen = Color.green(oldPixel)

                // Algorithm for getting new values after calculation of filter
                // Algorithm for GREY FILTER, by intensity of each pixel
                val intensity = (oldRed + oldBlue + oldGreen) / 3

                // applying new pixel values to newBitmap
                val newPixel = Color.rgb(intensity, intensity, intensity)
                newBitmap.setPixel(i, j, newPixel)
            }
        }
        return newBitmap
    }


    /**
     * Apply Negative Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    fun setNegativeFilter(oldBitmap: Bitmap): Bitmap {
        // copying to newBitmap for manipulation
        val newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true)

        // height and width of Image
        val h = newBitmap.height
        val w = newBitmap.width

        // traversing each pixel in Image as an 2D Array
        for (i in 0 until w) {
            for (j in 0 until h) {

                // getting each pixel
                val oldPixel = oldBitmap.getPixel(i, j)

                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                val oldRed = Color.red(oldPixel)
                val oldBlue = Color.blue(oldPixel)
                val oldGreen = Color.green(oldPixel)

                // Algorithm for getting new values after calculation of filter
                // Algorithm for NEGATIVE FILTER
                val newRed = HIGHEST_COLOR_VALUE - oldRed
                val newBlue = HIGHEST_COLOR_VALUE - oldBlue
                val newGreen = HIGHEST_COLOR_VALUE - oldGreen

                // applying new pixel value to newBitmap
                val newPixel = Color.rgb(newRed, newGreen, newBlue)
                newBitmap.setPixel(i, j, newPixel)
            }
        }
        return newBitmap
    }


    /**
     * Apply Monochrome Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    fun setBlackAndWhite(oldBitmap: Bitmap): Bitmap {
        val newBitmap = oldBitmap.copy(Bitmap.Config.RGB_565, true)
        val h = newBitmap.height
        val w = newBitmap.width

        // traversing each pixel in Image as an 2D Array
        for (i in 0 until w) {
            for (j in 0 until h) {

                // operating on each pixel
                val oldPixel = oldBitmap.getPixel(i, j)

                // each pixel is made from RED_BLUE_GREEN
                // so, getting current values of pixel
                val oldRed = Color.red(oldPixel)
                val oldGreen = Color.green(oldPixel)
                val oldBlue = Color.blue(oldPixel)

                // Algorithm for getting new values after calculation of filter
                // Algorithm for MONOCHROME FILTER
                val intensity = (oldRed + oldBlue + oldGreen) / 3

                // condition for monochrome
                val threshold = 128
                val binaryColor =
                    if (intensity > threshold) HIGHEST_COLOR_VALUE else LOWEST_COLOR_VALUE

                // applying new pixel value to newBitmap
                val newPixel = Color.rgb(binaryColor, binaryColor, binaryColor)
                newBitmap.setPixel(i, j, newPixel)
            }
        }

        return newBitmap
    }


    /**
     * Apply Automatic Thresholding on image
     * This filter convert image to binary image using automatic thresholding
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    fun automaticThresholding(oldBitmap: Bitmap): Bitmap {
        val grayBitmap = setGreyFilter(oldBitmap)
        val newBitmap = grayBitmap.copy(Bitmap.Config.RGB_565, true)
        val threshold = findThresholdAutomatically(grayBitmap)

        val h = newBitmap.height
        val w = newBitmap.width

        // traversing each pixel in Image as an 2D Array
        for (i in 0 until w) {
            for (j in 0 until h) {

                // operating on each pixel
                val oldPixel = oldBitmap.getPixel(i, j)

                // so, getting current values of pixel
                val intensity = Color.red(oldPixel)

                // condition for monochrome
                val binaryColor =
                    if (intensity > threshold) HIGHEST_COLOR_VALUE else LOWEST_COLOR_VALUE

                // applying new pixel value to newBitmap
                val newPixel = Color.rgb(binaryColor, binaryColor, binaryColor)
                newBitmap.setPixel(i, j, newPixel)
            }
        }

        return newBitmap
    }

    private fun findThresholdAutomatically(oldBitmap: Bitmap): Int {
        val tInitial = 192
        var tBefore = tInitial

        val h = oldBitmap.height
        val w = oldBitmap.width

        var loopCounter = 0
        while (true) {
            val g1: ArrayList<Int> = arrayListOf()
            val g2: ArrayList<Int> = arrayListOf()

            for (i in 0 until w) {
                for (j in 0 until h) {
                    val oldPixel = oldBitmap.getPixel(i, j)
                    val intensity = Color.red(oldPixel)

                    // condition for set g1 and g2
                    if (intensity > tBefore) {
                        g1.add(intensity)
                    } else {
                        g2.add(intensity)
                    }
                }
            }

            val m1 = g1.average().roundToInt()
            val m2 = g2.average().roundToInt()

            val tNew = (m1 + m2) / 2
            val absT = (tNew - tInitial).absoluteValue
            val deltaT = (tBefore - tNew).absoluteValue

            tBefore = tNew
            loopCounter++

            if (absT > deltaT && loopCounter > 2) break
        }

        return tBefore
    }
}