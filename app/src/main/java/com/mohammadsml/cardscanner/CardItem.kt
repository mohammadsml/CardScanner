package com.mohammadsml.cardscanner

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardItem(item: RecognizeItem){
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    Box {
        Image(
            modifier = Modifier
                .size(180.dp)
                .padding(start = 10.dp)
                .graphicsLayer{
                    alpha = 0.1f
                },
            painter = painterResource(item.bankKey!!.getResourceId()),
            contentDescription = "",

            )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    Color.White.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(Color.White.copy(alpha = 0.4f), Color.Transparent)
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
            ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally).padding(top = 15.dp),
                text = when(item.transferType){
                    RecognizeItem.TransferType.CARD -> item.source.panFormatter()
                    RecognizeItem.TransferType.IBAN -> item.source.ibanFormatter()
                    RecognizeItem.TransferType.PHONE -> item.source
                },
                color = Color.White,
                fontSize = if (item.transferType == RecognizeItem.TransferType.IBAN) 16.sp else 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, item.source)
                        }
                        val chooser = Intent.createChooser(intent, "Share via card scanner")
                        context.startActivity(chooser)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.05f), // glass transparency
                            shape = RoundedCornerShape(50.dp)
                        )
                        .fillMaxWidth(0.6f)
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.4f),
                                    Color.Transparent
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = "",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Share", color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(item.source))
                        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.05f), // glass transparency
                            shape = RoundedCornerShape(50.dp)
                        )
                        .fillMaxWidth(0.6f)
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.4f),
                                    Color.Transparent
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_copy),
                        contentDescription = "",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Copy", color = Color.White)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}


@Preview(name = "Home Screen Preview", showBackground = true, backgroundColor = 0xFF000000,)
@Composable
fun CardItemPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        CardItem(RecognizeItem.init())
    }
}