package com.pratyaksh.healthykingdom.ui.homepage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pratyaksh.healthykingdom.R
import com.pratyaksh.healthykingdom.domain.model.Users
import com.pratyaksh.healthykingdom.ui.HomeScreenViewModel
import com.pratyaksh.healthykingdom.ui.homepage.components.HomeScreenSearchbar
import com.pratyaksh.healthykingdom.ui.homepage.components.MapActionButtons
import com.pratyaksh.healthykingdom.ui.homepage.components.MapComponent
import com.pratyaksh.healthykingdom.ui.homepage.components.marker_detail_sheet.MarkerDetailsSheet
import com.pratyaksh.healthykingdom.ui.homepage.components.HospitalsCustomWindow
import com.pratyaksh.healthykingdom.ui.utils.HomeScreenDialogMenu
import com.pratyaksh.healthykingdom.ui.utils.LoadingComponent
import com.pratyaksh.healthykingdom.utils.Resource
import com.pratyaksh.healthykingdom.utils.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavHostController,
    logoutUser:()-> Flow<Resource<Boolean>>
){
    val context = LocalContext.current
    val mapView = remember{
        mutableStateOf( MapView(context) )
    }
    val sheetPeekHeight = remember{ mutableStateOf(0.dp) }

    LaunchedEffect(Unit){
        Configuration.getInstance().userAgentValue = context.packageName
    }

    LaunchedEffect(key1 = viewModel.homeScreenUiState.value.hospitals.size, block = {
        Log.d("MapMarkerLogs", "Adding map markers")
        viewModel.homeScreenUiState.value.hospitals.forEach {
            mapView.value.addHospitalToMap(it){marker ->
                closeAllInfoWindow(viewModel)
                viewModel.setBottomSheet(hospital = it)
                sheetPeekHeight.value = 90.dp
                mapView.value.controller.animateTo(it.location)
                viewModel.addMarkerWithInfoWindow(marker = marker)
            }.let {
                viewModel.addNewMarker(it)
            }
        }
    })

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val coroutine = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            MarkerDetailsSheet(uiState = viewModel.detailSheetUiState.value, onCloseClick = {
                coroutine.launch {
                    bottomSheetState.collapse()
                }
                sheetPeekHeight.value = 0.dp
                closeAllInfoWindow(viewModel)
            },
            onDetailsClick = {
                navController.navigate(Routes.HOSPITAL_DETAILS_SCREEN.route+"/$it")
            })
                       },
        sheetPeekHeight = sheetPeekHeight.value,
        sheetElevation = 12.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            MapComponent( mapView ){
                coroutine.launch {
                    bottomSheetState.collapse()
                }
                sheetPeekHeight.value = 0.dp
                closeAllInfoWindow(viewModel)
            }
            MapActionButtons()
            HomeScreenSearchbar(toggleMenu = { viewModel.toggleMainMenu(it) })

            if(viewModel.homeScreenUiState.value.isMainMenuVisible){
                HomeScreenDialogMenu(
                    userName = "User Name",
                    navController = navController,
                    onLogout = {
                        viewModel.toggleLoadingScr(true)
                        CoroutineScope(Dispatchers.IO).launch {
                            logoutUser()
                                .collectLatest {
                                    if(it.data == true){
                                        withContext(Dispatchers.Main){
                                            viewModel.toggleLoadingScr(false)
                                            navController.navigate(Routes.SIGNUP_NAVGRAPH.route){
                                                popUpTo(Routes.HOME_NAVGRAPH.route){ inclusive = true }
                                            }
                                        }
                                    }else{
                                        viewModel.toggleLoadingScr(false)
                                    }
                                }
                        }
                    },
                    onCloseMenu = { viewModel.toggleMainMenu(false) }
                )
            }
            if(viewModel.homeScreenUiState.value.isLoading) {
                Box(Modifier.fillMaxSize().background(Color(0x2D000000)), contentAlignment = Alignment.Center){
                    LoadingComponent(modifier = Modifier.size(80.dp))
                }
            }
        }
    }

}

fun closeAllInfoWindow(viewModel: HomeScreenViewModel){
    viewModel.apply {
        homeScreenUiState.value.markersWithInfoWindow.forEach {
            it.closeInfoWindow()
        }
        viewModel.clearInfoWindowMarkerList()
    }
}

fun MapView.addHospitalToMap(
    hospital: Users.Hospital,
    onMarkerClick:(marker:Marker) -> Unit,
): Marker{

    val newMarker = Marker(this).apply {
        position = hospital.location
        icon = ResourcesCompat.getDrawable(context.resources , R.drawable.icmark_hospital, null)
        title = hospital.name
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        subDescription = "This is ${hospital.name} location on the map"
        id = hospital.id
        Log.d("MarkerLogs", "Adding ${hospital.name}")
        infoWindow = HospitalsCustomWindow(this@addHospitalToMap, hospital)
        this.setOnMarkerClickListener { marker, mapView ->
            onMarkerClick(marker)
            showInfoWindow()
            true
        }
    }

    overlays.add(newMarker)
    invalidate()
    return newMarker

}

