package com.example.budget_plan.classes

class MatrixMulti {
    fun multiply(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
        val rowsA = a.size
        val colsA = a[0].size
        val rowsB = b.size
        val colsB = b[0].size

        require(colsA == rowsB) { "Number of columns of A must match number of rows of B" }

        val result = Array(rowsA) { DoubleArray(colsB) }

        for (i in 0 until rowsA) {
            for (j in 0 until colsB) {
                for (k in 0 until colsA) {
                    result[i][j] += a[i][k] * b[k][j]
                }
            }
        }

        return result
    }
}