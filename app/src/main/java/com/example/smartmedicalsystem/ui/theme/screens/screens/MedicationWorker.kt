package com.example.smartmedicalsystem.ui.theme.screens.screens
//package com.example.smartmedicalsystem.workers

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MedicationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val name = inputData.getString("name") ?: return Result.failure()
        val dosage = inputData.getString("dosage") ?: ""

        showNotification(name, dosage)

        return Result.success()
    }

    private fun showNotification(name: String, dosage: String) {

        val manager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, "med_channel")
            .setContentTitle("Medication Reminder")
            .setContentText("Take $name ($dosage)")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}