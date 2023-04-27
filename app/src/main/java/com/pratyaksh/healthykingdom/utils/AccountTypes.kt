package com.pratyaksh.healthykingdom.utils

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.pratyaksh.healthykingdom.R

sealed class AccountTypes(
    val type: String,
    val img: Int
){

    object AMBULANCE: AccountTypes( type = "Ambulance", img= R.drawable.ambulance )
    object HOSPITAL: AccountTypes( type = "Hospital", img= R.drawable.hospital )
    object PUBLIC_USER: AccountTypes( type = "Public User", img= R.drawable.ic_person )

}