package com.pratyaksh.healthykingdom.ui.hospital_registration

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.PhoneAuthProvider
import com.pratyaksh.healthykingdom.data.dto.HospitalsDto
import com.pratyaksh.healthykingdom.ui.utils.OtpTextField

@Composable
fun OtpVerifyScreen(
    activity: Activity,
    onVerify: ()->Unit,
    phone:String,
    verificationId: String,
    resendToken: PhoneAuthProvider.ForceResendingToken,
    viewModel: OtpValidationVM = hiltViewModel(),
    hospitalDto: HospitalsDto
){

    LaunchedEffect(Unit){
        viewModel.initScreen( verificationId, phone, resendToken, hospitalDto )
    }

    Column(
        Modifier.fillMaxSize()
            .padding(vertical= 16.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text= "Otp Sent to ${viewModel.uiState.phone}",
            textAlign = TextAlign.Center,
            fontWeight= FontWeight.Bold
        )

        Box(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ){

            Box{
                OtpTextField(
                    text = viewModel.uiState.code
                )

                TextField(
                    value = viewModel.uiState.code,
                    onValueChange = { viewModel.onCodeChange(it) },
                    modifier= Modifier
                        .matchParentSize()
                        .alpha(0f),
                )
            }

        }

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text= "Resend",
                color= if(viewModel.uiState.isResendAvail) Color.Blue else Color.LightGray,
                modifier= Modifier.clickable {
                    viewModel.otpSignInUseCase(
                        viewModel.uiState.phone,
                        activity,
                        resendToken= viewModel.uiState.resendToken!!,
                        onVerificationComplete = {
                            viewModel.addHospitalToFB()
                            onVerify()
                                                 },
                        onVerificationFailed = {
                            Log.d("VerificationLogs", "Can't verify otp request ${it.message}")
                        },
                        onCodeSent = viewModel::updateVerificationIdAndToken
                    )
                },
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.width(8.dp))

            CircularProgressIndicator(
                progress= viewModel.uiState.resendTimeout / 60f,
                modifier= Modifier.size(28.dp),
                strokeWidth = 4.dp
            )

        }

        Button(
            onClick={
                viewModel.addHospitalToFB()
                onVerify()
            },
            modifier = Modifier.fillMaxWidth(0.75f)
        ){
            Text(
                text = "Verify"
            )
        }



    }

}