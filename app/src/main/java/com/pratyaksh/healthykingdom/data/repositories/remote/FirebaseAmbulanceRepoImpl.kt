package com.pratyaksh.healthykingdom.data.repositories.remote

import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.pratyaksh.healthykingdom.data.dto.AmbulanceDto
import com.pratyaksh.healthykingdom.domain.repository.RemoteAmbulanceFbRepo
import com.pratyaksh.healthykingdom.utils.Constants
import kotlinx.coroutines.tasks.await

class FirebaseAmbulanceRepoImpl(
    val firestore: FirebaseFirestore
): RemoteAmbulanceFbRepo{
    override suspend fun addAmbulance(ambulanceDto: AmbulanceDto): Boolean {
        try {
            firestore.collection(Constants.Collections.AMBLANCE_DRIVERS)
                .document().set(ambulanceDto)
                .await()
            return true
        }catch(e: Exception){
            throw e
        }

    }

    override suspend fun getAmbulanceByPhone(phone: String): AmbulanceDto? {
        return firestore.collection(Constants.Collections.AMBLANCE_DRIVERS)
            .get().await()
            .toObjects(AmbulanceDto::class.java)
            .find {
                it.phone == phone
            }
    }

    override suspend fun getAllAmbulances(): List<AmbulanceDto> =
        firestore.collection(Constants.Collections.AMBLANCE_DRIVERS)
            .get().await()
            .toObjects(AmbulanceDto::class.java)

    override suspend fun getAllVacant(): List<AmbulanceDto> {
        return firestore.collection(Constants.Collections.AMBLANCE_DRIVERS)
            .get().await()
            .toObjects(AmbulanceDto::class.java)
            .filter {
                it.isOnline && it.isVacant
            }
    }

    override suspend fun getAllOccupiedAmbulance(): List<AmbulanceDto> {
        return firestore.collection(Constants.Collections.AMBLANCE_DRIVERS)
            .get().await()
            .toObjects(AmbulanceDto::class.java)
            .filter {
                it.isOnline && !it.isVacant
            }
    }

    override suspend fun getAllOnlineAmbulances(): List<AmbulanceDto> {
        return firestore.collection(Constants.Collections.AMBLANCE_DRIVERS)
            .get().await()
            .toObjects(AmbulanceDto::class.java)
            .filter {
                it.isOnline
            }
    }

    override suspend fun getAllOfflineAmbulances(): List<AmbulanceDto> {
        return firestore.collection(Constants.Collections.AMBLANCE_DRIVERS)
            .get().await()
            .toObjects(AmbulanceDto::class.java)
            .filter {
               !it.isOnline
            }
    }

    override suspend fun getAmbulanceByNumber(vehicleNum: String): AmbulanceDto? {
        return firestore.collection(Constants.Collections.AMBLANCE_DRIVERS)
            .get().await()
            .toObjects(AmbulanceDto::class.java)
            .find {
                it.ambulanceNumber == vehicleNum
            }
    }
}