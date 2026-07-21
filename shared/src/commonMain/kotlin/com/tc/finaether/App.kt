package com.tc.finaether

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tc.finaether.ui.screens.FinanceDashboardScreen
import com.tc.finaether.ui.screens.LoginScreen
import com.tc.finaether.ui.screens.ReportsScreen
import com.tc.finaether.ui.screens.SettingsScreen
import com.tc.finaether.ui.screens.TransactionScreen

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1A73E8),
            secondary = Color(0xFF5F6368),
            tertiary = Color(0xFF174EA6)
        )
    ) {
        if (currentScreen == Screen.Login) {
            LoginScreen(onLoginSuccess = {
                currentScreen = Screen.Dashboard
            })
        } else {
            MainScaffold(
                currentScreen = currentScreen,
                onNavigate = { currentScreen = it }
            )
        }
    }
}

@Composable
fun MainScaffold(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") },
                    selected = currentScreen == Screen.Dashboard,
                    onClick = { onNavigate(Screen.Dashboard) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Assessment, contentDescription = "Reports") },
                    label = { Text("Reports") },
                    selected = currentScreen == Screen.Reports,
                    onClick = { onNavigate(Screen.Reports) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = currentScreen == Screen.Settings,
                    onClick = { onNavigate(Screen.Settings) }
                )
            }
        },
        floatingActionButton = {
            if (currentScreen == Screen.Dashboard) {
                FloatingActionButton(onClick = { onNavigate(Screen.AddTransaction) }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Transaction")
                }
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (currentScreen) {
                Screen.Dashboard -> FinanceDashboardScreen(onAddTransaction = { onNavigate(Screen.AddTransaction) })
                Screen.Reports -> ReportsScreen()
                Screen.Settings -> SettingsScreen()
                Screen.AddTransaction -> TransactionScreen(onBack = { onNavigate(Screen.Dashboard) })
                else -> {}
            }
        }
    }
}

sealed class Screen {
    data object Login : Screen()
    data object Dashboard : Screen()
    data object Reports : Screen()
    data object Settings : Screen()
    data object AddTransaction : Screen()
}
