package com.fakhry.pengolahancitra

import com.fakhry.pengolahancitra.helpers.pattern_recognition.RgbImageToHsv
import org.junit.Assert
import org.junit.Test

class RgbImageToHsvTest {

    @Test
    fun `hsvToRgb convertBlack success`() {
        // arrange
        val hue = 0f
        val saturation = 0f
        val value = 0f

        // act
        val result = RgbImageToHsv.hsvToRgb(hue, saturation, value)
        val expectedResult = intArrayOf(0, 0, 0)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `hsvToRgb convertWhite success`() {
        // arrange
        val hue = 0f
        val saturation = 0f
        val value = 1f

        // act
        val result = RgbImageToHsv.hsvToRgb(hue, saturation, value)
        val expectedResult = intArrayOf(255, 255, 255)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `hsvToRgb convertRed success`() {
        // arrange
        val hue = 0f
        val saturation = 1f
        val value = 1f

        // act
        val result = RgbImageToHsv.hsvToRgb(hue, saturation, value)
        val expectedResult = intArrayOf(255, 0, 0)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `hsvToRgb convertLime success`() {
        // arrange
        val hue = 120f
        val saturation = 1f
        val value = 1f

        // act
        val result = RgbImageToHsv.hsvToRgb(hue, saturation, value)
        val expectedResult = intArrayOf(0, 255, 0)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `hsvToRgb convertBlue success`() {
        // arrange
        val hue = 240f
        val saturation = 1f
        val value = 1f

        // act
        val result = RgbImageToHsv.hsvToRgb(hue, saturation, value)
        val expectedResult = intArrayOf(0, 0, 255)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `hsvToRgb convertCyan success`() {
        // arrange
        val hue = 180f
        val saturation = 1f
        val value = 1f

        // act
        val result = RgbImageToHsv.hsvToRgb(hue, saturation, value)
        val expectedResult = intArrayOf(0, 255, 255)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `hsvToRgb convertMagenta success`() {
        // arrange
        val hue = 300f
        val saturation = 1f
        val value = 1f

        // act
        val result = RgbImageToHsv.hsvToRgb(hue, saturation, value)
        val expectedResult = intArrayOf(255, 0, 255)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `hsvToRgb convertSilver success`() {
        // arrange
        val hue = 0f
        val saturation = 0f
        val value = 0.75f

        // act
        val result = RgbImageToHsv.hsvToRgb(hue, saturation, value)
        val expectedResult = intArrayOf(191, 191, 191)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `rgbToHsv convertRed success`() {
        // arrange
        val red = 255
        val green = 0
        val blue = 0

        // act
        val result = RgbImageToHsv.rgbToHsv(red, green, blue)
        val expectedResult = floatArrayOf(0f, 1f, 1f)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `rgbToHsv convertGreen success`() {
        // arrange
        val red = 0
        val green = 128
        val blue = 0

        // act
        val result = RgbImageToHsv.rgbToHsv(red, green, blue)
        val expectedResult = floatArrayOf(120f, 1f, 0.5019608f)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }

    @Test
    fun `rgbToHsv convertBlue success`() {
        // arrange
        val red = 0
        val green = 0
        val blue = 255

        // act
        val result = RgbImageToHsv.rgbToHsv(red, green, blue)
        val expectedResult = floatArrayOf(240f, 1f, 1f)

        // assert
        Assert.assertEquals(expectedResult[0], result[0])
        Assert.assertEquals(expectedResult[1], result[1])
        Assert.assertEquals(expectedResult[2], result[2])
    }
}