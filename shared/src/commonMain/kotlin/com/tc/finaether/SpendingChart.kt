package com.tc.finaether

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tc.finaether.core.models.Transaction

@Composable
fun SpendingChart(transactions: List<Transaction>) {
    val categories = transactions.groupBy { it.category }
    val categoryTotals = categories.mapValues { (_, trans) -> trans.sumOf { it.amount } }
    val totalAmount = categoryTotals.values.sum()

    if (totalAmount == 0.0) {
        Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
            Text("No data available", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Spending Breakdown", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            var startAngle = 0f
            val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Cyan, Color.Magenta)
            
            categoryTotals.values.forEachIndexed { index, amount ->
                val sweepAngle = (amount / totalAmount).toFloat() * 360f
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    size = Size(size.height, size.height),
                    topLeft = Offset((size.width - size.height) / 2, 0f)
                )
                startAngle += sweepAngle
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Legend
        categoryTotals.keys.forEachIndexed { index, category ->
            Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Cyan, Color.Magenta)
                Box(modifier = Modifier.size(12.dp).background(colors[index % colors.size]))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = category, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$${categoryTotals[category]}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
