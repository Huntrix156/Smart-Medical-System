
package com.example.nexora.viewmodel  // ✅ fix package name (not .data.Reminder)

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nexora.data.Reminder.AlarmScheduler
import com.example.smartmedicalsystem.models.Reminder

class ReminderViewModel(application: Application) : AndroidViewModel(application) {


    fun scheduleReminder(reminder: Reminder) {
        val context = getApplication<Application>() // ✅ safe way to get context
        AlarmScheduler.setAlarm(context, reminder)
    }

    fun cancelReminder(reminder: Reminder) {
        val context = getApplication<Application>()
        AlarmScheduler.cancelAlarm(context, reminder)
    }
}