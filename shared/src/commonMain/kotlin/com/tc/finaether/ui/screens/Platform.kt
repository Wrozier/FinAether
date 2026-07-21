package com.tc.finaether.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tc.finaether.FinanceDashboardState
import com.tc.finaether.SpendingChart
import com.tc.finaether.core.models.Goal
import com.tc.finaether.core.models.Recommendation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceDashboardScreen(onAddTransaction: () -> Unit) {

    var state by remember { mutableStateOf(FinanceDashboardState()) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FinAether", fontWeight = FontWeight.Companion.Bold) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransaction) {
                //Icon(Icons.Companion.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.Companion
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Welcome back!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Companion.Bold
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Adaptive Budget Card
            BudgetCard(state)

            Spacer(modifier = Modifier.Companion.height(24.dp))

            // Spending Analysis
            Text("Spending Analysis", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Card(
                modifier = Modifier.Companion.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                        alpha = 0.3f
                    )
                )
            ) {
                SpendingChart(state.recentTransactions)
            }

            Spacer(modifier = Modifier.Companion.height(24.dp))

            Text("Investment Recommendations", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.Companion.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.recommendations) { rec ->
                    InvestmentCard(rec)
                }
            }

            Spacer(modifier = Modifier.Companion.height(24.dp))

            // Goal Progress
            GoalProgressSection(state.goalsProgress)
        }
    }
}

@Composable
fun BudgetCard(state: FinanceDashboardState) {
    Card(
        modifier = Modifier.Companion.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.Companion.padding(16.dp)) {
            Text("Monthly Summary", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.Companion.height(12.dp))

            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Income", style = MaterialTheme.typography.labelMedium)
                    Text(
                        "$${state.monthlyIncome}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF4CAF50)
                    )
                }
                Column {
                    Text("Expenses", style = MaterialTheme.typography.labelMedium)
                    Text(
                        "$${state.monthlyExpenses}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Column {
                    Text("Savings Rate", style = MaterialTheme.typography.labelMedium)
                    Text(
                        "${(state.savingsRate * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.Companion.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.Companion.height(12.dp))

            Text(state.budgetSummary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun InvestmentCard(recommendation: Recommendation) {
    Card(
        modifier = Modifier.Companion.width(220.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.Companion.padding(16.dp)) {
            Text(
                recommendation.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Companion.Bold
            )
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Text(
                recommendation.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3
            )
            Spacer(modifier = Modifier.Companion.height(8.dp))
            TextButton(onClick = { /* TODO */ }, contentPadding = PaddingValues(0.dp)) {
                Text("View Details")
            }
        }
    }
}

@Composable
fun GoalProgressSection(goals: List<Goal>) {
    Column {
        Text("Financial Goals", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.Companion.height(12.dp))
        goals.forEach { goal ->
            GoalItem(goal)
            Spacer(modifier = Modifier.Companion.height(16.dp))
        }
    }
}

@Composable
fun GoalItem(goal: Goal) {
    val progressValue = if (goal.target > 0) (goal.current / goal.target).toFloat() else 0f

    Column(modifier = Modifier.Companion.fillMaxWidth()) {
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                goal.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Companion.Medium
            )
            Text("$${goal.current} / $${goal.target}", style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.Companion.height(6.dp))
        LinearProgressIndicator(
            progress = { progressValue },
            modifier = Modifier.Companion.fillMaxWidth().height(8.dp),
            strokeCap = StrokeCap.Companion.Round
        )
    }
}