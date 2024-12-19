package com.oguzhanaslann.dynamicicon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

fun scheduleChangeLauncherActivity(
    activitiesEnabled: Array<String>,
    activitiesDisabled: Array<String>,
) {
    val data = Data.Builder()
        .putStringArray("enabled", activitiesEnabled)
        .putStringArray("disabled", activitiesDisabled)
        .build()

    val constraints = Constraints.Builder()
//        .setRequiresDeviceIdle(true)
        .build()

    val workRequest = OneTimeWorkRequest.Builder(ChangeLauncherActivityWorker::class.java)
        .setInputData(data)
        .setConstraints(constraints)
        .setInitialDelay(10, TimeUnit.SECONDS)
        .build()

    val workManager = WorkManager.getInstance()
    workManager.enqueue(workRequest)
}

private fun changeEnabledComponent(
    context: Context,
    activitiesEnabled: Array<String>,
    activitiesDisabled: Array<String>,
) {
    val packageManager: PackageManager = context.packageManager
    val packageName: String = context.packageName

    activitiesEnabled.forEach {
        packageManager.setComponentEnabledSetting(
            ComponentName(packageName, it),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    activitiesDisabled.forEach {
        packageManager.setComponentEnabledSetting(
            ComponentName(packageName, it),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}

class ChangeLauncherActivityWorker(
    private val appContext: Context,
    private val workerParams: WorkerParameters,
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val data = workerParams.inputData
        val activitiesEnabled = data.getStringArray("enabled")
        val activitiesDisabled = data.getStringArray("disabled")
        return try {
            Log.d("HUE", "ChangeLauncherActivityWorker - doWork")
            changeEnabledComponent(appContext, activitiesEnabled!!, activitiesDisabled!!)
            Result.success()
        } catch (e: Exception) {
            Log.e("HUE", "2doWork exception: $e")
            Result.failure()
        }
    }
}
