package com.tc.finaether

import com.tc.finaether.core.models.Goal
import com.tc.finaether.core.models.Recommendation
import com.tc.finaether.core.models.Transaction

data class FinanceDashboardState(
    val monthlyIncome: Double = 0.0,
    val monthlyExpenses: Double = 0.0,
    val savingsRate: Double = 0.0,
    val budgetSummary: String = "",
    val recommendations: List<Recommendation> = emptyList(),
    val goalsProgress: List<Goal> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList()
) {
    fun update(newState: FinanceDashboardState): FinanceDashboardState {
        return newState
    }
}
