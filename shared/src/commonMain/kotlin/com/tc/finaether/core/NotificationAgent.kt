package com.tc.finaether.core

import com.tc.finaether.core.models.BudgetAlert
import com.tc.finaether.core.models.Event
import com.tc.finaether.core.models.InvestmentOpportunity

class NotificationAgent {
    suspend fun sendSmartNotification(userId: String, event: Event) {
        val message = when (event) {
            is BudgetAlert -> "You're $45 over budget in Dining. Want to adjust?"
            is InvestmentOpportunity -> "New opportunity in AI stocks matching your profile."
        }
        // Send push notification
    }
}