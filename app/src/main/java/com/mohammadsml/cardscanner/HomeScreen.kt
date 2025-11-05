package com.mohammadsml.cardscanner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    items: List<RecognizeItem>
) {

    val aboutState = remember { mutableStateOf(false) }

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
        if (aboutState.value){
            AboutScreen(
                pageState = {aboutState.value = false}
            )
        }else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 50.dp)
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Card Scanner", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text("Recognize your text or photo", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Icon(
                            painter = painterResource(R.drawable.ic_about),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .clickable {
                                    aboutState.value = true
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
                    }
                }

                item {
                    if (items.size == 0){
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text("not found 404 :(", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp,
                                modifier = Modifier.align(alignment = Alignment.Center).padding(top = 100.dp))
                        }

                    }
                }

                itemsIndexed(items) { _, item ->
                    CardItem(item = item)
                }
            }
        }
    }
}


@Preview(name = "Home Screen Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun HomeScreenPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        HomeScreen(RecognizeItem.test())
    }
}