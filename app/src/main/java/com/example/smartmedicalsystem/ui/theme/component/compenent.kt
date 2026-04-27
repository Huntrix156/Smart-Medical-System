package com.example.nexora.ui.theme.screens.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.airbnb.lottie.compose.LottieAnimation
//import com.airbnb.lottie.compose.LottieCompositionSpec
//import com.airbnb.lottie.compose.LottieConstants
//import com.airbnb.lottie.compose.animateLottieCompositionAsState
//import com.airbnb.lottie.compose.rememberLottieComposition
//import com.example.nexora.domain.model.DoseStatus
//import com.example.nexora.domain.model.MedicationDose
//
//
//@Composable
//fun LottieAnimationWidget(lottiePath:Int,size: Dp ) {
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottiePath))
//    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
//    LottieAnimation(
//        composition = composition,
//        progress = { progress },
//        modifier = Modifier.size(300.dp)
//    )
//}
//
//
//
//@Composable
//fun MedicationDoseCard(
//    dose: MedicationDose,
//    onMarkTaken: () -> Unit
//) {
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//
//        Column(modifier = Modifier.padding(16.dp)) {
//
//            Text(text = "Medicine ID: ${dose.medicationId}")
//            Text(text = "Time: ${dose.time}")
//
//            when (dose.status) {
//
//                DoseStatus.TAKEN -> Text("Taken ✅")
//
//                DoseStatus.MISSED -> Text("Missed ❌")
//
//                DoseStatus.PENDING -> Button(
//                    onClick = onMarkTaken
//                ) {
//                    Text("Mark as Taken")
//                }
//            }
//        }
//    }
////}
//
//
//
//
//
//
//class AlarmReceiver : BroadcastReceiver() {
//
//    override fun onReceive(context: Context, intent: Intent) {
//        showNotification(context)
//    }
//
//    private fun showNotification(context: Context) {
//        // your notification code here
//    }
//}}
//
//
//@Composable
//fun SettingItem(
//    title: String,
//    subtitle: String? = null,
//    icon: ImageVector,
//    trailing: @Composable (() -> Unit)? = null,
//    onClick: () -> Unit = {}
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() }
//            .padding(vertical = 12.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        Icon(icon, contentDescription = title)
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        Column(modifier = Modifier.weight(1f)) {
//            Text(text = title, fontWeight = FontWeight.Medium)
//            if (subtitle != null) {
//                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
//            }
//        }
//
//        trailing?.invoke()
//    }
//}


@Composable
fun Label(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = Color.Gray,
        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
    )
}