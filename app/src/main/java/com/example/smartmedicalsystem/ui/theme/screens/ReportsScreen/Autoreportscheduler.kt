package com.example.smartmedicalsystem.ui.theme.screens.ReportsScreen

import com.example.smartmedicalsystem.data.ReportViewModel
import android.content.Context
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat




class DailyReportWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val vm = ReportViewModel()
        val label = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        vm.generateHospitalReport("daily", label)
        vm.generateSystemAnalytics("daily", label)
        return Result.success()
    }
}

class WeeklyReportWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val vm = ReportViewModel()
        val cal = Calendar.getInstance()
        val weekStart = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).run {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            format(cal.time)
        }
        val label = "Week of $weekStart"
        vm.generateHospitalReport("weekly", label)
        vm.generateSystemAnalytics("weekly", label)
        return Result.success()
    }
}


class MonthlyReportWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val vm = ReportViewModel()
        val label = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
        vm.generateHospitalReport("monthly", label)
        vm.generateSystemAnalytics("monthly", label)
        return Result.success()
    }
}



object AutoReportScheduler {

    fun schedule(context: Context) {
        val workManager = WorkManager.getInstance(context)

        val dailyRequest = PeriodicWorkRequestBuilder<DailyReportWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setInitialDelay(initialDelayUntilMidnight(), TimeUnit.MILLISECONDS)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()

        workManager.enqueueUniquePeriodicWork(
            "auto_daily_report",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyRequest
        )


        val weeklyRequest = PeriodicWorkRequestBuilder<WeeklyReportWorker>(
            repeatInterval = 7,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setInitialDelay(initialDelayUntilNextMonday(), TimeUnit.MILLISECONDS)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()

        workManager.enqueueUniquePeriodicWork(
            "auto_weekly_report",
            ExistingPeriodicWorkPolicy.KEEP,
            weeklyRequest
        )


        val monthlyRequest = PeriodicWorkRequestBuilder<MonthlyReportWorker>(
            repeatInterval = 30,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setInitialDelay(initialDelayUntilFirstOfMonth(), TimeUnit.MILLISECONDS)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()

        workManager.enqueueUniquePeriodicWork(
            "auto_monthly_report",
            ExistingPeriodicWorkPolicy.KEEP,
            monthlyRequest
        )
    }

    private fun initialDelayUntilMidnight(): Long {
        val now = Calendar.getInstance()
        val midnight = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return (midnight.timeInMillis - now.timeInMillis).coerceAtLeast(0)
    }

    private fun initialDelayUntilNextMonday(): Long {
        val now = Calendar.getInstance()
        val nextMonday = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(now)) add(Calendar.WEEK_OF_YEAR, 1)
        }
        return (nextMonday.timeInMillis - now.timeInMillis).coerceAtLeast(0)
    }

    private fun initialDelayUntilFirstOfMonth(): Long {
        val now = Calendar.getInstance()
        val firstOfMonth = Calendar.getInstance().apply {
            add(Calendar.MONTH, 1)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return (firstOfMonth.timeInMillis - now.timeInMillis).coerceAtLeast(0)
    }
}

