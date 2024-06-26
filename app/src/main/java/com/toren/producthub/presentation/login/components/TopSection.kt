package com.toren.producthub.presentation.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toren.producthub.R


@Composable
fun TopSection(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f),
            painter = painterResource(
                id = R.drawable.screen_shape
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f)
                .padding(top = 100.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_app),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                            .padding(4.dp)
                    )
                    Column {
                        Text(
                            text = "FastBuy",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    Text(
                        text = "all your needs...",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    }
                }
            }
            Text(
                text = "Welcome to FastBuy",
                fontStyle = MaterialTheme.typography.headlineSmall.fontStyle,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

