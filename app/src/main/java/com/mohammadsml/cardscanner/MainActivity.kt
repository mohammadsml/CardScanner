package com.mohammadsml.cardscanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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