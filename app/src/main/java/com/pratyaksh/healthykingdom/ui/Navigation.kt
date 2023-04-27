package com.pratyaksh.healthykingdom.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pratyaksh.healthykingdom.homeScreenNavGraph
import com.pratyaksh.healthykingdom.registrationNavgraph
import com.pratyaksh.healthykingdom.ui.homepage.HomeScreen
import com.pratyaksh.healthykingdom.ui.hospital_details.HospitalDetailsScreen
import com.pratyaksh.healthykingdom.ui.hospital_registration.OtpVerifyScreen
import com.pratyaksh.healthykingdom.ui.hospital_registration.RegisterHospital
import com.pratyaksh.healthykingdom.utils.Routes

@Composable
fun Navigation(
    startDestination: Routes,
    activity: Activity
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
    ){

        homeScreenNavGraph(navController)

        registrationNavgraph(
            startDestination = Routes.LOGIN_SCREEN,
            activity = activity,
            navController = navController
        )


    }



}