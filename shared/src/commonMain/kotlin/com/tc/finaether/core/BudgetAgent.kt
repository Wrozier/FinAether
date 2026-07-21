package com.tc.finaether.core

import com.tc.finaether.core.models.BudgetPlan
import com.tc.finaether.core.models.Expense

class BudgetAgent {
    suspend fun generateBudgetPlan(userIncome: Double, expenses: List<Expense>): BudgetPlan {
        val prompt = "Income: $userIncome. Expenses: $expenses. Create optimized monthly budget."
        // LLM call
        return BudgetPlan()
    }
}