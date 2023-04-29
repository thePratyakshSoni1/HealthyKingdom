package com.pratyaksh.healthykingdom.ui


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.pratyaksh.healthykingdom.domain.model.Hospital
import com.pratyaksh.healthykingdom.domain.use_case.getHospital.GetAllHospitalsUseCase
import com.pratyaksh.healthykingdom.ui.homepage.HomeScreenUiState
import com.pratyaksh.healthykingdom.ui.homepage.components.marker_detail_sheet.MarkerDetailSheetUiState
import com.pratyaksh.healthykingdom.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.osmdroid.views.overlay.Marker
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val getAllHospitalsUseCase: GetAllHospitalsUseCase,
    private val saveState: SavedStateHandle
): ViewModel() {

    val homeScreenUiState = mutableStateOf( HomeScreenUiState() )

    @OptIn(SavedStateHandleSaveableApi::class)
    var user by saveState.saveable { mutableStateOf<String>("") }
        private set

    val detailSheetUiState = mutableStateOf( MarkerDetailSheetUiState(
        isLoading = false,
        hospitalName = "",
        hospitalId = "",
        listOf(),
        listOf(),
        listOf(),
    ) )

    init {
        getAllHospitals()
    }

    private fun getAllHospitals(){

        getAllHospitalsUseCase().onEach {
            when(it){

                is Resource.Success -> {

                    homeScreenUiState.value = homeScreenUiState.value.copy(
                        hospitals = it.data ?: emptyList(),
                        isLoading = false
                    )
                    Log.d("ViewmodelLogs", "Hospitals retreived: ${it.data}")
                }
                is Resource.Loading -> {

                    homeScreenUiState.value = homeScreenUiState.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {

                    homeScreenUiState.value = homeScreenUiState.value.copy(
                        isLoading = false,
                        isError = true
                    )
                }

            }
        }.launchIn(viewModelScope)

    }

    fun setBottomSheetLoading( isLoading:Boolean = true ){
        detailSheetUiState.value = detailSheetUiState.value.copy(isLoading)
    }
    fun setBottomSheet( hospital: Hospital ){
        viewModelScope.launch {
            setBottomSheetLoading(true)
            delay(2500L)
            detailSheetUiState.value = detailSheetUiState.value.copy(
                isLoading = false,
                hospital.name,
                hospital.id,
                hospital.availBloods,
                hospital.availPlasma,
                hospital.availPlatelets
            )
            setBottomSheetLoading(false)
        }
    }

    fun addNewMarker(marker: Marker){
        homeScreenUiState.value = homeScreenUiState.value.copy(
            mapUiState = HomeScreenUiState.MapMarkersUiState(homeScreenUiState.value.mapUiState.markers + marker.position)
        )
    }

    fun addMarkerWithInfoWindow(marker:Marker){
        homeScreenUiState.value = homeScreenUiState.value.copy(
            markersWithInfoWindow = homeScreenUiState.value.markersWithInfoWindow + marker
        )
    }

    fun clearInfoWindowMarkerList(){
        homeScreenUiState.value = homeScreenUiState.value.copy(
            markersWithInfoWindow = emptyList()
        )
    }



}