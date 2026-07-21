package com.tc.finaether.core.models

import androidx.compose.runtime.Composable
import com.tc.finaether.core.orchestrator.AetherOrchestrator
import com.tc.finaether.core.BudgetAgent
import com.tc.finaether.core.FinanceRecommendationAgent
import com.tc.finaether.core.orchestrator.LLMClient


data class BankAccount(val name: String, val balance: Double)

data class Transaction(val amount: Double, val category: String, val description: String)

data class Recommendation(val id: String = "", val title: String = "", val description: String = "")

data class Goal(val name: String = "", val target: Double = 0.0, val current: Double = 0.0)

data class Expense(val amount: Double, val category: String)

data class BudgetPlan(val id: String = "")

data class ExportResult(val success: Boolean, val filePath: String)

data class UserProfile(val id: String = "")

data class FinancePlan(val id: String = "")

data class UserIntent(val action: String, val params: Map<String, Any> = emptyMap())

data class UserContext(val userId: String, val preferences: Map<String, String> = emptyMap())

data class FinanceMetrics(val data: Map<String, Any> = emptyMap())

data class Improvement(val description: String = "")

data class MarketData(val symbol: String = "", val price: Double = 0.0, val trend: String = "")

data class InvestmentSuggestion(val title: String = "", val description: String = "", val riskLevel: String = "")

sealed class Event
data class BudgetAlert(val message: String) : Event()
data class InvestmentOpportunity(val description: String) : Event()

val currentUserContext = UserContext("default_user")

object AetherLogger {
    fun info(message: String) {
        println("INFO: $message")
    }
}

@Composable
inline fun <reified T> koinInject(): T {
    // Placeholder for Koin injection to resolve compilation errors.
    return when (T::class) {
        AetherOrchestrator::class -> AetherOrchestrator(
            BudgetAgent(),
            FinanceRecommendationAgent(object : LLMClient {
                override suspend fun generate(prompt: String): String = "AI Response"
            })
        ) as T
        else -> throw IllegalArgumentException("Unknown type for koinInject")
    }
}
