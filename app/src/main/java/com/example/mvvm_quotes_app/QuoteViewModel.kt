package com.example.mvvm_quotes_app

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {
    private var nextId = 1

    private val _quoteList = mutableStateListOf<Quote>()
    val quoteList: List<Quote> = _quoteList

    var quoteText by mutableStateOf("")
    var author by mutableStateOf("")

    fun addQuote() {
        if (quoteText.isNotBlank() && author.isNotBlank()) {
            _quoteList.add(Quote(nextId++, quoteText.trim(), author.trim()))
            quoteText = ""
            author = ""
        }
    }

    fun deleteLastQuote() {
        if (_quoteList.isNotEmpty()) {
            _quoteList.removeAt(_quoteList.lastIndex)
        }
    }

}
