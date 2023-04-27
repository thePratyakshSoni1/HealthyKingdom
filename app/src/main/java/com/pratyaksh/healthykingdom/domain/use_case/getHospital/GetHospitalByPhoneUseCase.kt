package com.pratyaksh.healthykingdom.domain.use_case.getHospital

import com.pratyaksh.healthykingdom.data.dto.toHospital
import com.pratyaksh.healthykingdom.domain.model.Hospital
import com.pratyaksh.healthykingdom.domain.repository.RemoteFirebaseRepo
import javax.inject.Inject

class GetHospitalByPhoneUseCase @Inject constructor(
    private val fbRepo: RemoteFirebaseRepo
) {

    suspend operator fun invoke(phone: String, password: String): Hospital?{
        return fbRepo.getHospitalByPhone(phone, password)?.toHospital()
    }

}