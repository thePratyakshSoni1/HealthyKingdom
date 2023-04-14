package com.pratyaksh.healthykingdom.ui.homepage

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.pratyaksh.healthykingdom.R
import com.pratyaksh.healthykingdom.data.dto.toMapsGeopoint
import com.pratyaksh.healthykingdom.domain.model.Hospital
import com.pratyaksh.healthykingdom.ui.HomeScreenViewModel
import com.pratyaksh.healthykingdom.ui.homepage.components.HomeScreenSearchbar
import com.pratyaksh.healthykingdom.ui.homepage.components.MapActionButtons
import com.pratyaksh.healthykingdom.ui.homepage.components.MapComponent
import com.pratyaksh.healthykingdom.ui.homepage.components.marker_detail_sheet.MarkerDetailsSheet
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val mapView = remember{
        mutableStateOf( MapView(context) )
    }

    LaunchedEffect(Unit){
        Configuration.getInstance().userAgentValue = context.packageName
    }

    LaunchedEffect(key1 = viewModel.allHospitals.size, block = {
        viewModel.allHospitals.forEach {
            mapView.value.addHospitalToMap(it)
        }
    })

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = { MarkerDetailsSheet(uiState = viewModel.detailSheetUiState.value ) },
        sheetPeekHeight = 116.dp,
        sheetElevation = 12.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            MapComponent( mapMarkers = viewModel.allHospitals, mapView )
            MapActionButtons()
            HomeScreenSearchbar()

        }
    }



}

fun MapView.addHospitalToMap(
    hospital: Hospital,

){

    val newMarker = Marker(this).apply {
        position = hospital.location.toMapsGeopoint()
        icon = ResourcesCompat.getDrawable(context.resources , R.drawable.icmark_hospital, null)
        title = hospital.name
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        subDescription = "This is ${hospital.name} location on the map"
        id = hospital.id
        Log.d("MarkerLogs", "Adding ${hospital.name}")
    }

    overlays.add(newMarker)
    invalidate()


}