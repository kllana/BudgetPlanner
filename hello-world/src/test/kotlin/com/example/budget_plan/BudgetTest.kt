package com.example.budget_plan

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import kotlin.test.assertTrue

class BudgetTest {

    @Test
    fun `calculate remaining budget should return correct value`() {
        val initialBudget = 10_000
        val expenses = 3_000
        val income = 2_000

        val remainingBudget = calculateRemainingBudget(initialBudget, expenses, income)

        assertEquals(9_000, remainingBudget, "Ожидаемый остаток бюджета: 9,000")
    }

    @Test
    fun `exceeding budget should trigger warning`() {
        val initialBudget = 5_000
        val expenses = 7_000

        val result = checkBudgetExceedance(initialBudget, expenses)

        assertTrue(result, "Ожидается предупреждение о превышении бюджета")
    }

    // Функции для расчета бюджета
    private fun calculateRemainingBudget(budget: Int, expenses: Int, income: Int): Int {
        return budget - expenses + income
    }

    private fun checkBudgetExceedance(budget: Int, expenses: Int): Boolean {
        return expenses > budget
    }

    @Test
    fun `calculate with zero initial budget`() {
        val initialBudget = 0
        val expenses = 1_000
        val income = 500

        val remainingBudget = calculateRemainingBudget(initialBudget, expenses, income)

        assertEquals(-500, remainingBudget, "Ожидаемый остаток бюджета: -500")
    }

    @Test
    fun `negative initial budget calculation`() {
        val initialBudget = -5_000
        val expenses = 3_000
        val income = 2_000

        val remainingBudget = calculateRemainingBudget(initialBudget, expenses, income)

        assertEquals(-6_000, remainingBudget, "Ожидаемый остаток бюджета: -6,000")
    }

    @Test
    fun `expenses equal to budget`() {
        val initialBudget = 5_000
        val expenses = 5_000
        val income = 2_000

        val remainingBudget = calculateRemainingBudget(initialBudget, expenses, income)

        assertEquals(2_000, remainingBudget, "Ожидаемый остаток бюджета: 2,000")
    }

}