package com.example.smartmedicalsystem.data

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.telephony.SmsManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicalsystem.models.SOSState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue   // ✅ ADD THIS
import androidx.compose.runtime.setValue  // ✅ ADD THIS


class SOSViewModel(
    private val context: Context
) : ViewModel() {

    var sosState by mutableStateOf(SOSState.IDLE)
        private set

    var countdown by mutableStateOf(5)
        private set

    private var countdownJob: Job? = null

    private val emergencyContacts = listOf(
        "0712345678",
        "0798765432"
    )

    // 🔴 Start Long Press
    fun startHolding() {
        sosState = SOSState.HOLDING
    }

    // 🔴 Release → Start Countdown
    fun startCountdown() {
        sosState = SOSState.COUNTDOWN
        countdown = 5

        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            triggerSOS()
        }
    }

    // ❌ Cancel SOS
    fun cancelSOS() {
        countdownJob?.cancel()
        sosState = SOSState.IDLE
    }

    // 🚨 FINAL TRIGGER
    private fun triggerSOS() {
        sosState = SOSState.ACTIVE

        viewModelScope.launch(Dispatchers.IO) {
            val location = getLocation()
            val message = buildMessage(location)

            sendSMS(message)
            makeCall(emergencyContacts.first())
            playAlarm()
        }
    }

    // 📍 LOCATION
    private suspend fun getLocation(): Pair<Double, Double>? {
        // Simplified (replace with FusedLocationProviderClient)
        return Pair(-1.2921, 36.8219) // Nairobi fallback
    }

    private fun buildMessage(location: Pair<Double, Double>?): String {
        return if (location != null) {
            "EMERGENCY! I need help.\n" +
                    "https://maps.google.com/?q=${location.first},${location.second}"
        } else {
            "EMERGENCY! I need help. Location unavailable."
        }
    }

    // 📩 SMS
    private fun sendSMS(message: String) {
        val smsManager = SmsManager.getDefault()
        emergencyContacts.forEach {
            smsManager.sendTextMessage(it, null, message, null, null)
        }
    }

    // 📞 CALL
    private fun makeCall(number: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$number")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    // 🔊 ALARM
    private fun playAlarm() {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(context, uri)
        ringtone.play()
    }

    fun stopSOS() {
        sosState = SOSState.IDLE
    }
}