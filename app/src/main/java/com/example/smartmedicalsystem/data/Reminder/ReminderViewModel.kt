package com.example.smartmedicalsystem.data.Reminder

import android.Manifest
import android.app.Application
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import com.example.smartmedicalsystem.models.Reminder

class ReminderViewModel(application: Application) : AndroidViewModel(application) {


    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun scheduleReminder(reminder: Reminder) {
        val context = getApplication<Application>() // ✅ safe way to get context
        AlarmScheduler.setAlarm(context, reminder)
    }

    fun cancelReminder(reminder: Reminder) {
        val context = getApplication<Application>()
        AlarmScheduler.cancelAlarm(context, reminder)
    }
}