package com.example.goodwill

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "Message received")

        remoteMessage.notification?.let {
            Log.d("FCM", "Notification payload: Title=${it.title}, Body=${it.body}")
            showNotification(it.title ?: "Title", it.body ?: "Body")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send this token to your server if needed
        Log.d("FCMToken", "New Token: $token")
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "channel_id"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel =
            NotificationChannel(channelId, "Default Channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()


        notificationManager.notify(0, notification)
    }
}
