package com.pratyaksh.healthykingdom.ui.homepage.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import com.pratyaksh.healthykingdom.R
import com.pratyaksh.healthykingdom.data.dto.toMapsGeopoint
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.ITileSource
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

@Composable
fun MapComponent(
    mapAndroidVew: State<MapView>,
    onMapSingleClick:()->Unit
){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        AndroidView(
            factory = { mapAndroidVew.value },
            modifier = Modifier.matchParentSize()
        ){

            Log.d("ComposeLogs", "Re-composed MapComponent")
            it.setTileSource(TileSourceFactory.MAPNIK)
            it.controller.zoomTo(5, 2500L)

//            val clickListeners = MapEventsOverlay(object: MapEventsReceiver{
//                override fun singleTapConfirmedHelper(geoPoint: GeoPoint?): Boolean {
//                    onMapSingleClick()
//                    return true
//                }
//
//                override fun longPressHelper(p: GeoPoint?): Boolean {
//                    return false
//                }
//            })
//            it.overlays.add(clickListeners)

            val rotationalGestOverlay = RotationGestureOverlay(it)
            it.setMultiTouchControls(true)
            it.overlays.add(rotationalGestOverlay)
            it.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        }

    }


}