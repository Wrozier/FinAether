package com.tc.finaether.core.evolution

import com.tc.finaether.core.models.AetherLogger
import com.tc.finaether.core.models.FinanceMetrics
import com.tc.finaether.core.models.Improvement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface FinanceMetricsCollector {
    suspend fun collect(): FinanceMetrics
}

interface FinanceEvolutionAgent {
    suspend fun propose(metrics: FinanceMetrics): List<Improvement>
}

class FinanceEvoLoop(
    private val metricsCollector: FinanceMetricsCollector,
    private val evolutionAgent: FinanceEvolutionAgent,
    private val scope: CoroutineScope
) {
    private val _improvements = MutableStateFlow<List<Improvement>>(emptyList())
    val improvements: StateFlow<List<Improvement>> = _improvements.asStateFlow()

    fun start() {
        scope.launch {
            while (true) {
                try {
                    val metrics = metricsCollector.collect()
                    val newImprovements = evolutionAgent.propose(metrics)
                    _improvements.value = (_improvements.value + newImprovements).takeLast(10)
                    applyImprovements(newImprovements)
                } catch (e: Exception) {
                    AetherLogger.info("Evolution Loop Error: ${e.message}")
                }
                delay(300_000) // 5 minutes
            }
        }
    }

    private fun applyImprovements(improvements: List<Improvement>) {
        improvements.forEach { improvement ->
            AetherLogger.info("Applying improvement: ${improvement.description}")
        }
    }
}
