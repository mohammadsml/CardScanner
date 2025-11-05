package com.mohammadsml.cardscanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mohammadsml.cardscanner.ui.theme.CardScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardScannerTheme {
                val recognizeItems = remember { mutableStateListOf<RecognizeItem>() }
                when {
                    intent?.action == Intent.ACTION_SEND -> {
                        if ("text/plain" == intent.type) {
                            RecognizeText().handleSendText(
                                intent,
                                onFindText = {
                                    recognizeItems.addAll(it)
                                })
                        } else if (intent.type?.startsWith("image/") == true) {
                            RecognizeText().handleSendImage(
                                intent, this,
                                onFindText = {
                                    recognizeItems.addAll(it)
                                })
                        }
                    }
                }

                HomeScreen(recognizeItems)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CardScannerTheme {

    }
}