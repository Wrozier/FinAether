package com.tc.finaether.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tc.finaether.core.models.koinInject
import com.tc.finaether.core.orchestrator.AetherOrchestrator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {
    val orchestrator: AetherOrchestrator = koinInject()
    var isGenerating by remember { mutableStateOf(false) }
    var reportSummary by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Financial Reports", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "AI Insights",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Generate a detailed analysis of your spending habits and financial health using Aether AI.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = {
                            isGenerating = true
                            // Simulate AI report generation
                            // orchestrator.process(UserIntent("generate_report", mapOf("type" to "monthly")))
                            reportSummary = "Your spending in 'Dining' increased by 15% this month. However, you saved $200 more than last month. Good job!"
                            isGenerating = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isGenerating
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Generate Monthly AI Report")
                        }
                    }
                }
            }

            if (reportSummary != null) {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Report Summary",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(reportSummary!!, style = MaterialTheme.typography.bodyLarge)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = { /* TODO: Export PDF */ }) {
                                Text("Export to PDF")
                            }
                        }
                    }
                }
            }
        }
    }
}
