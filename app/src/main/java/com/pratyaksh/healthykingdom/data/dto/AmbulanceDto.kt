package com.pratyaksh.healthykingdom.data.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.pratyaksh.healthykingdom.domain.model.Users

data class AmbulanceDto(
    val driverName: String = "",
    val ambulanceNumber: String = "",
    val driverAge: Int = 0,
    val driverGender: String = "",
    val vacant: Boolean = false,
    val online: Boolean = false,
    val password: String? = null,
    var phone: String? = null,
    val location: GeoPoint? = null,
    val userId: String? = null,
    val mail: String = "",
    val lastLocUpdated: Timestamp? = null,
)

fun AmbulanceDto.toAmbulance(): Users.Ambulance{

    val gender = when(driverGender){

        "F" -> "Female"
        "M" -> "Male"
        else -> "Other"

    }

    return Users.Ambulance(
        driverName = driverName,
        driverGender = gender,
        driverAge = driverAge,
        vehicleLocation = location!!.toMapsGeopoint(),
        vehicleNumber = ambulanceNumber,
        isOnline = online,
        isVacant = vacant,
        lastLocUpdated = lastLocUpdated,
        userId = userId,
        password = password,
        phone = phone,
        mail= mail
    )

}
