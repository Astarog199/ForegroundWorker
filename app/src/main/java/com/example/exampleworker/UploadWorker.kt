package com.example.exampleworker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class UploadWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    private val channelId = "CHANNEL_ID"
    private val notificationId = 1
    private var notificationManager= appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    var count = 0

    override suspend fun doWork(): Result {
        withContext(Dispatchers.Main){
            Toast.makeText(applicationContext, "Run", Toast.LENGTH_SHORT).show()
        }

        while (count < 11){
            createNotification(count)
            count++
            delay(1000)
        }

        withContext(Dispatchers.Main){
            Toast.makeText(applicationContext, "Complete", Toast.LENGTH_SHORT).show()
        }
        return Result.success()
    }

    private fun createNotification(counter: Int) {
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

        notificationManager.notify(notificationId, notification)
    }
}