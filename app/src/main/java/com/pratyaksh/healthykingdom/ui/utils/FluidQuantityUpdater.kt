package com.pratyaksh.healthykingdom.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratyaksh.healthykingdom.utils.BloodGroups
import com.pratyaksh.healthykingdom.utils.LifeFluids
import com.pratyaksh.healthykingdom.utils.Plasma

@Preview(showBackground = true)
@Composable
private fun Previewcompose(){
    Box(
        modifier=Modifier.fillMaxSize()
    ){

        Column(Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally){
            FluidQuantityUpdater(qty = 10, type= LifeFluids.BLOOD, group = BloodGroups.B_POSITIVE, onIncQty = { Unit }, onDecQty = { Unit })
        }

    }
}


@Composable
fun FluidQuantityUpdater(
    type: LifeFluids,
    group: BloodGroups,
    onIncQty:()->Unit,
    onDecQty:()->Unit,
    qty: Int,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier.padding(4.dp)
    ){
        Box(
            Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (type == LifeFluids.BLOOD) Color.Red else Color(0xFFAC2D2D)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text= when(group){
                    BloodGroups.A_POSITIVE -> "A+"
                    BloodGroups.A_NEGATIVE -> "A-"
                    BloodGroups.B_POSITIVE -> "B+"
                    BloodGroups.B_NEGATIVE -> "B-"
                    BloodGroups.O_POSITIVE -> "O+"
                    BloodGroups.O_NEGATIVE -> "O-"
                    BloodGroups.AB_POSITIVE -> "AB+"
                    BloodGroups.AB_NEGATIVE -> "AB-"
                },
                fontSize = 18.sp,
                color=Color.White,
                fontWeight = FontWeight.Bold,
            )
        }

        FluidQuantityTxtField(
            onIncQty = onIncQty,
            onDecQty = onDecQty,
            qty = qty
        )
    }
}

@Composable
private fun FluidQuantityTxtField(
    onIncQty:()->Unit,
    onDecQty:()->Unit,
    qty: Int,
){

    Row(
        Modifier.wrapContentWidth(),
        verticalAlignment= Alignment.CenterVertically
    ){
        IconButton(
            icon = Icons.Rounded.Close,
            onClick = { onDecQty() }, iconColor = Color.Red, backgroundColor = Color.Transparent,
            size = 8.dp
        )
        Text(
            modifier= Modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(4.dp)),
            text= "$qty ltrs"
        )
        IconButton(icon = Icons.Rounded.Add, onClick = { onIncQty() }, iconColor = Color.Green, backgroundColor = Color.Transparent, size = 8.dp)


    }

}

@Composable
fun FluidQuantityUpdater(
    qty: Int,
    group: Plasma,
    onIncQty:()->Unit,
    onDecQty:()->Unit,

){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEEB300)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text= when(group){
                    Plasma.PLASMA_A -> "A"
                    Plasma.PLASMA_B -> "B"
                    Plasma.PLASMA_AB -> "AB"
                    Plasma.PLASMA_O -> "O"
                },
                fontSize = 18.sp,
                color=Color.White,
                fontWeight = FontWeight.Bold,
            )
        }

        FluidQuantityTxtField(
            onIncQty = onIncQty,
            onDecQty = onDecQty,
            qty = qty
        )
    }

}