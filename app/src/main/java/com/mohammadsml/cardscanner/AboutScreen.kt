package com.mohammadsml.cardscanner

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen(
    pageState: () -> Unit = {}
) {

    val context = LocalContext.current
    val githubUrl = remember { mutableStateOf("https://github.com/mohammadsml/CardScanner") }
    val linkedinUrl = remember { mutableStateOf("https://www.linkedin.com/in/mohammadreza-safarmohammadloo-75aa0a83") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1C0202), Color(0xFF050505))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(R.drawable.ic_scan),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(end = 16.dp, top = 50.dp)
                .clickable {
                    pageState()
                }
                .size(55.dp)
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
                .padding(10.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(
                    color = Color.White.copy(alpha = 0.08f), // glass transparency
                    shape = RoundedCornerShape(24.dp)
                )
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Featured for banking services",
                color = Color.Gray,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("CARD SCANNER", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp)


            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Card, IBAN, Phone Number\nrecognition from text or image",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Just share your text or photo to the app",
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl.value))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .height(48.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.0f), // glass transparency
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
                    painter = painterResource(R.drawable.ic_github),
                    contentDescription = "",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Github", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl.value))
                    context.startActivity(intent)
                },
                text = "Linkedin",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Made by Mohammadsml",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}


@Preview(
    name = "About Screen Preview",
    showBackground = true,
    backgroundColor = 0xFF000000,
)
@Composable
fun AboutScreenPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        AboutScreen()
    }
}