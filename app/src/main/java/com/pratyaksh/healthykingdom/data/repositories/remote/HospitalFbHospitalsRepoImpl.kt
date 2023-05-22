package com.pratyaksh.healthykingdom.data.repositories.remote

import com.pratyaksh.healthykingdom.domain.repository.RemoteHospitalFbRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.GeoPoint
import com.pratyaksh.healthykingdom.data.dto.AvailFluidsDto
import com.pratyaksh.healthykingdom.data.dto.HospitalsDto
import com.pratyaksh.healthykingdom.data.dto.lifefluids.AvailBloodDto
import com.pratyaksh.healthykingdom.data.dto.lifefluids.AvailPlasmaDto
import com.pratyaksh.healthykingdom.data.dto.lifefluids.AvailPlateletsDto
import com.pratyaksh.healthykingdom.domain.model.Users
import com.pratyaksh.healthykingdom.utils.Constants.Collections
import kotlinx.coroutines.tasks.await

class HospitalFbHospitalsRepoImpl(private val fireStore: FirebaseFirestore): RemoteHospitalFbRepo {
    override suspend fun getAllHospitals(): List<HospitalsDto> {
        try{
            return fireStore.collection(Collections.HOSPITALS_COLLECTION)
                .get().await()
                .toObjects(HospitalsDto::class.java)
        }catch(e: Exception){
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun getHospitalById(id: String): HospitalsDto? {
        try{
            return fireStore.collection(Collections.HOSPITALS_COLLECTION)
                .document(id).get().await()
                .toObject(HospitalsDto::class.java)
        }catch(e: Exception){
            e.printStackTrace()
            throw e
        }
    }
//    override suspend fun getHospitalById(id: String): HospitalsDto? {
//        try{
//            getAllHospitals()
//                .forEach {
//                    if(it.id == id){
//                        return it
//                    }
//                }
//            return null
//        }catch(e: Exception){
//            e.printStackTrace()
//            throw e
//        }
//    }

    override suspend fun getHospitalByPhone(phone: String): HospitalsDto? {
        try{
            return getAllHospitals().find {
                it.phone == phone
            }
        }catch(e: Exception){
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun addHospital(hospital: HospitalsDto): Boolean{

        try{
            val task1 = fireStore.collection(Collections.HOSPITALS_COLLECTION)
                .document(hospital.id).set(hospital)

            val task2 = fireStore.collection(Collections.LIFE_FLUIDS)
                .document(hospital.id).set(
                    AvailFluidsDto(
                        AvailBloodDto(),
                        AvailPlasmaDto(),
                        AvailPlateletsDto()
                    )
                )

            task1.await()
            task2.await()

            return true
        }catch(e: FirebaseFirestoreException){
            e.printStackTrace()
            throw e
        }

    }
    override suspend fun getHospitalByLocation(geoPoint: GeoPoint): Users.Hospital {
        TODO("Not yet implemented")
    }

    override suspend fun getHospitalsNearby(geoPoint: GeoPoint): List<Users.Hospital> {
        TODO("Not yet implemented")
    }


}