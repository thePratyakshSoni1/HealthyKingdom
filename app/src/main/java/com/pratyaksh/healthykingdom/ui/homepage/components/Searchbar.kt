package com.pratyaksh.healthykingdom.ui.homepage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratyaksh.healthykingdom.ui.utils.IconButton

@Composable
fun HomeScreenSearchbar() {

    Box(
        modifier = Modifier
            .padding(top= 10.dp)
            .fillMaxWidth(0.95f)
            .fillMaxHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(horizontal = 14.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                imageVector = Icons.Default.Search,
                contentDescription = "search hospitals",
                modifier = Modifier
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(Color.LightGray)
            )
            Spacer(Modifier.width(8.dp))

            Text(
                text= "Search Hospitals...",
                color = Color.LightGray,
            )
            Spacer(Modifier.width(8.dp))

            IconButton(
                icon = Icons.Default.AccountCircle, onClick = { Unit },
                backgroundColor = Color.LightGray,
                iconColor = Color.White,
                size = 2.5.dp
            )

        }
    }

}