package com.example.expensetracker.api

data class ApiResponse(val conversion_rates: ResponseCurrencies)

data class ResponseCurrencies(
        val TRY: Float,
        val USD: Float,
        val GBP: Float)