package com.pratyaksh.healthykingdom.domain.use_case.getFluidsData

import com.pratyaksh.healthykingdom.data.dto.lifefluids.toBloodModel
import com.pratyaksh.healthykingdom.data.dto.lifefluids.toPlasmaModel
import com.pratyaksh.healthykingdom.data.dto.lifefluids.toPlateletsModel
import com.pratyaksh.healthykingdom.domain.model.lifefluids.LifeFluidsModel
import com.pratyaksh.healthykingdom.domain.repository.RemoteLifeFluidsFbRepo
import com.pratyaksh.healthykingdom.utils.LifeFluids
import com.pratyaksh.healthykingdom.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSpecificFluidByHospitalUseCase @Inject constructor(
    private val fluidsRepo: RemoteLifeFluidsFbRepo
) {

    operator fun invoke(
        hospitalId: String,
        fluidtype: LifeFluids
    ) = flow<Resource<LifeFluidsModel>>{
        try{
            emit(Resource.Loading("Fetching data..."))
            fluidsRepo.getLifeFluidFromHospital(hospitalId)?.let {
                emit( Resource.Success(
                    when(fluidtype){
                        LifeFluids.PLASMA ->  it.plasma!!.toPlasmaModel()
                        LifeFluids.BLOOD -> it.bloods!!.toBloodModel()
                        LifeFluids.PLATELETS -> it.platelets!!.toPlateletsModel()
                    }
                ))
            } ?: emit(Resource.Error("Unexpected eror occured !"))

        }catch(e: Exception){
            e.printStackTrace()
            emit(Resource.Error("Unexpected eror occured !"))
        }
    }


}