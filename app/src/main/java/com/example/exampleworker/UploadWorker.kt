package com.example.exampleworker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class UploadWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    private val channelId = "MyWORK"
    private val notificationId = 1
    private var notificationManager= appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    var count = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        withContext(Dispatchers.Main){
            Toast.makeText(applicationContext, "Run", Toast.LENGTH_SHORT).show()
        }

        while (count < 11){
            val process = "process: $count"
            setForeground(createNotification(process))
            count++
            delay(1000)
        }

        withContext(Dispatchers.Main){
            Toast.makeText(applicationContext, "Complete", Toast.LENGTH_SHORT).show()
        }

        notificationManager.deleteNotificationChannel(channelId)

        return Result.success()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(counter: String):ForegroundInfo {
        val channel = NotificationChannel(
            channelId,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val intent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("UploadWorker")
            .setTicker("Worker")
            .setContentText("Run: $counter")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .setSilent(true)
            .addAction(android.R.drawable.ic_delete, "Complete", intent)
            .build()

       return ForegroundInfo(notificationId, notification)
    }
}