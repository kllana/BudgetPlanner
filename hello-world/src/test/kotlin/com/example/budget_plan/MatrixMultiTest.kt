package com.example.budget_plan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import com.example.budget_plan.classes.MatrixMulti


class MatrixMultiTest {

    private val multiplier = MatrixMulti();

    @Test
    fun testCorrectMultiplier() {
        val a = arrayOf(
            doubleArrayOf(1.5, 2.0),
            doubleArrayOf(3.2, 4.1)
        )

        val b = arrayOf(
            doubleArrayOf(2.0, 0.5),
            doubleArrayOf(1.0, 2.0)
        )

        val expected = arrayOf(
            doubleArrayOf(5.0, 4.0),
            doubleArrayOf(10.4, 9.47)
        )

        val result = multiplier.multiply(a, b)

        for (i in expected.indices) {
            for (j in expected[i].indices) {
                assertEquals(expected[i][j], result[i][j], 1.0, "Mismatch at [$i][$j]")
            }
        }
    }

    @Test
    fun testRectangularMatrices() {
        val a = arrayOf(
            doubleArrayOf(1.0, 2.0, 3.0)
        )

        val b = arrayOf(
            doubleArrayOf(4.0),
            doubleArrayOf(5.0),
            doubleArrayOf(6.0)
        )

        val expected = arrayOf(doubleArrayOf(32.0))

        val result = multiplier.multiply(a, b)

        assertEquals(expected[0][0], result[0][0], 0.01, "Mismatch for rectangular matrices")
    }

    @Test
    fun testResultWithZeros() {
        val a = arrayOf(
            doubleArrayOf(1.0, 2.0),
            doubleArrayOf(3.0, 4.0)
        )

        val b = arrayOf(
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(0.0, 0.0)
        )

        val expected = arrayOf(
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(0.0, 0.0)
        )

        val result = multiplier.multiply(a, b)

        for (i in expected.indices) {
            for (j in expected[i].indices) {
                assertEquals(expected[i][j], result[i][j], 0.01, "Mismatch at [$i][$j]")
            }
        }
    }


    @Test
    fun testIncorrectMultiplier() {
        val a = arrayOf(
            doubleArrayOf(1.0, 2.0)
        )

        val b = arrayOf(
            doubleArrayOf(1.0, 2.0),
            doubleArrayOf(3.0, 4.0),
            doubleArrayOf(5.0, 6.0)
        )

        try {
            multiplier.multiply(a, b)
            assert(false) { "Exception expected due to incompatible matrix dimensions" }
        } catch (e: IllegalArgumentException) {
            assert(e.message?.contains("Number of columns of A") == true)
        }
    }

}