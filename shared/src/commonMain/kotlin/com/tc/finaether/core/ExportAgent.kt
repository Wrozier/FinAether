package com.tc.finaether.core

import com.tc.finaether.core.models.ExportResult

class ExportAgent {
    suspend fun exportReport(format: String = "PDF"): ExportResult {
        // Generate PDF/CSV report with AI summary
        return ExportResult(success = true, filePath = "report_${System.currentTimeMillis()}.pdf")
    }
}