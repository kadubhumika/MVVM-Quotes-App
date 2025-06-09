package com.example.mvvm_quotes_app

import androidx.compose.runtime.mutableStateListOf

object QuoteManager {
    private val quoteList = mutableStateListOf<Quote>()

    fun listQuotes(): List<Quote> {
        return quoteList
    }

    fun insertQuote(quote: Quote) {
        quoteList.add(quote)
    }

    fun deleteQuote(id: Int) {
        quoteList.removeIf { it.id == id }
    }
}