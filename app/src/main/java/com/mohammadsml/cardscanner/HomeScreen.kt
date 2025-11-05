package com.mohammadsml.cardscanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    items : List<RecognizeItem>
){

    if (items.size == 0){
        AboutScreen()
    }else{
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
            LazyColumn(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 40.dp )) {
                itemsIndexed(items) { index , item ->

                    Box() {
                        Image(
                            modifier = Modifier
                                .size(180.dp)
                                .padding(start = 10.dp)
                                .graphicsLayer{
                                    alpha = 0.2f
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

                            ) {
                            Text(
                                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                                //text = if (item.transferType == RecognizeItem.TransferType.CARD)item.source.panFormatter() else item.source,
                                text = when(item.transferType){
                                    RecognizeItem.TransferType.CARD -> item.source.panFormatter()
                                    RecognizeItem.TransferType.IBAN -> item.source.ibanFormatter()
                                    RecognizeItem.TransferType.PHONE -> item.source
                                },
                                color = Color.White,
                                fontSize = if (item.transferType == RecognizeItem.TransferType.IBAN) 16.sp else 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}



@Preview(name = "Home Screen Preview", showBackground = true, backgroundColor = 0xFF000000,)
@Composable
fun HomeScreenPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        HomeScreen(listOf(RecognizeItem.init(), RecognizeItem.init(),RecognizeItem.init(), RecognizeItem.init(),RecognizeItem.init(),RecognizeItem.init()))
    }
}