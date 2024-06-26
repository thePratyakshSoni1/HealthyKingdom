package com.pratyaksh.healthykingdom.ui.homepage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.pratyaksh.healthykingdom.R
import com.pratyaksh.healthykingdom.domain.model.Users
import com.pratyaksh.healthykingdom.ui.fluid_update.NavMenuItem
import com.pratyaksh.healthykingdom.ui.utils.IconButton
import com.pratyaksh.healthykingdom.utils.AccountTypes
import com.pratyaksh.healthykingdom.utils.Routes
import com.pratyaksh.healthykingdom.utils.identifyUserTypeFromId

@Composable
fun HomeScreenDialogMenu(
    userName: String,
    userId: String,
    navController: NavHostController,
    onLogout: () -> Unit,
    onCloseMenu: () -> Unit
) {

    Dialog(onDismissRequest = { onCloseMenu() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.99f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFFD8EAFF))
                .padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 42.dp)
        ) {

            Spacer(Modifier.height(6.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Healthy Kingdom",
                    color = Color(0xFF353535),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    icon = Icons.Rounded.Close,
                    onClick = { onCloseMenu() },
                    backgroundColor = Color.Transparent
                )
            }
            Spacer(Modifier.height(6.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(Modifier.width(6.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_person),
                        contentDescription = null,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = userName,
                        color = Color(0xFF353535),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                NavMenuItem(title = "Profile", imageIcon = Icons.Rounded.AccountCircle) {
                    navController.navigate(Routes.PROFILE_SCREEN.route)
                }


                if ( identifyUserTypeFromId(userId)!!.equals(AccountTypes.HOSPITAL) ) {
                    NavMenuItem(title = "Update Fluids", imageIcon = Icons.Rounded.Edit) {
                        navController.navigate(Routes.FLUIDS_UPDATION_NAVGRAPH.route)
                    }
                    NavMenuItem(title = "Update Requests", imageIcon = painterResource(id = R.drawable.requests)) {
                        navController.navigate(Routes.REQUESTS_UPDATION_SCREEN.withArgs(userId))
                    }
                }

                NavMenuItem(title = "Settings", imageIcon = Icons.Rounded.Settings) {
                    navController.navigate(Routes.SETTINGS_SCREEN.route)
                }
                NavMenuItem(title = "Log out", imageIcon = Icons.Rounded.ExitToApp) {
                    onLogout()
                }

            }
        }
    }

}