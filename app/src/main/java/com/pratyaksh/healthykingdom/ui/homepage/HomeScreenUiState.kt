package com.pratyaksh.healthykingdom.ui.homepage

import com.pratyaksh.healthykingdom.domain.model.Users
import com.pratyaksh.healthykingdom.ui.homepage.components.marker_filters.FilterOption
import com.pratyaksh.healthykingdom.ui.homepage.components.marker_filters.MarkerFilters
import com.pratyaksh.healthykingdom.utils.AccountTypes
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

data class HomeScreenUiState(

    val accountType: AccountTypes = AccountTypes.PUBLIC_USER,
    val searchText: String= "",
    val hospitals: List<Users.Hospital> = emptyList(),
    val mapUiState: MapMarkersUiState = MapMarkersUiState( emptyList() ),
    val markersWithInfoWindow: List<Marker> = emptyList(),
    val mapActionButtonsUiState: MapActionButtonsUiState = MapActionButtonsUiState(true, false),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isMainMenuVisible: Boolean = false,
    val userId: String? = null,
    val filters: List<FilterOption> = emptyList(),
    val selectedFilter: MarkerFilters

){

    data class MapMarkersUiState(
        val markers: List<GeoPoint>
    )

    data class MapActionButtonsUiState(
        val areHospitalsVisible: Boolean,
        val areOnlyHospitalsVisible: Boolean
    )

}
