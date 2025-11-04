package com.mohammadsml.cardscanner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    recognizeItems : List<RecognizeItem>
){

    val pagerState = rememberPagerState(
        pageCount = { recognizeItems.size },
        initialPage = if (recognizeItems.indexOf(recognizeItems.first()) != -1) recognizeItems.indexOf(
            recognizeItems.first()
        ) else 0
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF3A0404), Color(0xFF050505))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        VerticalPager(
            state = pagerState,
            beyondViewportPageCount = 3,
            pageSize = PageSize.Fixed(180.dp),
            contentPadding = PaddingValues(vertical = 200.dp),
            pageSpacing = 10.dp
        ) { pageIndex ->

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
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
                    .padding(24.dp)
            ) {
                Text(
                    text = "Card #$pageIndex",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
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