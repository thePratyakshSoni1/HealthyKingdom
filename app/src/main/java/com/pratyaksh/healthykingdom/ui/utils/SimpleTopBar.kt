package com.pratyaksh.healthykingdom.ui.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleTopBar(
    onBackPress:()->Unit,
    title: String,
    EndButtons:@Composable ()->Unit = { Unit }
){
    TopAppBar(
        elevation = 4.dp,
        backgroundColor = Color.White,
    ) {
        Row(
            modifier= Modifier
                .fillMaxSize()
                .padding(end = 8.dp),
            verticalAlignment= Alignment.CenterVertically
        ) {
            IconButton(icon = Icons.Rounded.KeyboardArrowLeft, onClick = onBackPress, size= 16.dp)
            Spacer(Modifier.width(6.dp))

            Text(text = title, fontWeight = FontWeight.Bold, modifier=Modifier.weight(1f), color = Color.Black, fontSize= 16.sp)
            EndButtons()
        }
    }

}