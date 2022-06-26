package com.example.pecodetest.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.pecodetest.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.random.Random

class NotificationUtil @Inject constructor(@ApplicationContext private val context: Context) {
    private val notificationChannelId = "main_notification_channel_of_pecodetest_app"
    private val notificationChannelName = "MainNotificationChannelOfPecodeTestApp"
    private val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    private val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    fun sendNotificationWithIntent(
        intent: Intent,
        title: String,
        body: String,
        id: Int = Random.nextInt(0, 100000)
    ) {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        } else {
            PendingIntent.FLAG_CANCEL_CURRENT
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flags)
        val notificationBuilder = NotificationCompat.Builder(context, notificationChannelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        createNotificationChannel(context)
        notificationManager.notify(id, notificationBuilder.build())
    }

    fun cancelNotification(id: Int) = notificationManager.cancel(id)

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager: NotificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}