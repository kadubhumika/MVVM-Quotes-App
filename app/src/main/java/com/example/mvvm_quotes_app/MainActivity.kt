package com.example.mvvm_quotes_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.material3.OutlinedTextField

import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mvvm_quotes_app.Quote
import com.example.mvvm_quotes_app.QuoteManager


import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvvm_quotes_app.ui.theme.MVVMQuotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVVMQuotesAppTheme {
                MainScreen()


            }
        }
    }
}
@Composable
fun MainScreen() {
    val showQuotesApp = remember { mutableStateOf(false) }

    if (showQuotesApp.value) {
        QuotesApp()
    } else {
        SplashScreen(onStartClicked = {
            showQuotesApp.value = true
        })
    }
}


@Composable
fun SplashScreen(onStartClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.wrapContentSize().padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onStartClicked,
                        ) {
                        Text(text = "Enter App",fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun QuotesApp() {
    val quotes = QuoteManager.listQuotes()
    var quoteText by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = quoteText,
            onValueChange = { quoteText = it },
            label = { Text("Enter Quote") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Enter Author") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                if (quoteText.isNotEmpty() && author.isNotEmpty()) {
                    QuoteManager.insertQuote(Quote(nextId++, quoteText, author))
                    quoteText = ""
                    author = ""
                }
            }) {
                Text("Add Quote")
            }

            Button(onClick = {
                if (nextId > 1) {
                    QuoteManager.deleteQuote(nextId - 1)
                    nextId--
                }
            }) {
                Text("Delete Last")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Quotes List", style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                quotes.forEach {
                    Text(text = "\"${it.text}\"", style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold)
                    Text(text = "- ${it.author}", style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 7.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}



