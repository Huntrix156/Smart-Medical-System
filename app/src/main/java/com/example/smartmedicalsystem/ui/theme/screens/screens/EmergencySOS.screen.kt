package com.example.smartmedicalsystem.ui.theme.screens.screens

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.telephony.SmsManager
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*


enum class SOSState {
    IDLE, HOLDING, COUNTDOWN, ACTIVE
}


class SOSController(private val context: Context) {

    var sosState by mutableStateOf(SOSState.IDLE)
    var countdown by mutableStateOf(5)

    private var job: Job? = null

    private val contacts = listOf(
        "0712345678",
        "0798765432"
    )

    fun startHolding() {
        sosState = SOSState.HOLDING
    }

    fun startCountdown(scope: CoroutineScope) {
        sosState = SOSState.COUNTDOWN
        countdown = 5

        job?.cancel()
        job = scope.launch {
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            triggerSOS()
        }
    }

    fun cancelSOS() {
        job?.cancel()
        sosState = SOSState.IDLE
    }

    private fun triggerSOS() {
        sosState = SOSState.ACTIVE

        val message = "EMERGENCY! I need help.\nhttps://maps.google.com/?q=-1.2921,36.8219"

        sendSMS(message)
        makeCall(contacts.first())
        playAlarm()
    }

    private fun sendSMS(msg: String) {
        val sms = SmsManager.getDefault()
        contacts.forEach {
            sms.sendTextMessage(it, null, msg, null, null)
        }
    }

    private fun makeCall(number: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$number")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    private fun playAlarm() {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(context, uri)
        ringtone.play()
    }

    fun stopSOS() {
        sosState = SOSState.IDLE
    }
}


@Composable
fun EmergencySOSScreen(context: Context) {

    val scope = rememberCoroutineScope()
    val controller = remember { SOSController(context) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        when (controller.sosState) {

            SOSState.IDLE, SOSState.HOLDING -> {
                SOSButton(controller, scope)
            }

            SOSState.COUNTDOWN -> {
                CountdownView(controller)
            }

            SOSState.ACTIVE -> {
                ActiveSOSView(controller)
            }
        }
    }
}



@Composable
fun SOSButton(controller: SOSController, scope: CoroutineScope) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(200.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        controller.startHolding()

                        val released = tryAwaitRelease()

                        // ✅ THIS IS WHERE IT GOES
                        if (released) {
                            controller.startCountdown(scope)
                        } else {
                            controller.cancelSOS()
                        }
                    }
                )
            }
    ) {

        Button(
            onClick = {},
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("SOS", color = Color.White, fontSize = 32.sp)
        }

        if (controller.sosState == SOSState.HOLDING) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            )
        }
    }
}


@Composable
fun CountdownView(controller: SOSController) {
    val context= LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween, // 🔥 IMPORTANT
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Sending alert in ${controller.countdown}...",
            color = Color.White,
            fontSize = 22.sp
        )


        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = {
                val phone = "+254700063070"

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()

        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Dial Icon",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Dial",
                fontSize = 30.sp)


        }



        OutlinedButton(
            onClick = {
                val uri = Uri.parse("smsto:0700063070")

                val intent = Intent(Intent.ACTION_SENDTO, uri)

                intent.putExtra("Hello", "How is todays weather")

                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()

        ) {
            Icon(
                imageVector = Icons.Default.Sms,
                contentDescription = "sms Icon",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sms",
                fontSize = 30.sp)


        }


        Button(onClick = { controller.cancelSOS() }) {
            Text("CANCEL")
        }
    }
}


@Composable
fun ActiveSOSView(controller: SOSController) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {


        Text(
            text = "SOS ACTIVE",
            color = Color.Red,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Contacts notified", color = Color.White)
        Text("Calling emergency contact...", color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { controller.stopSOS() }) {
            Text("STOP SOS")
        }
    }
}
