package com.example.android_notes_app

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.android_notes_app.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Log the message
        Log.d("FCM", "Message received from: ${remoteMessage.from}")

        // Handle notification payload (if any)
        remoteMessage.notification?.let {
            Log.d("FCM", "Notification Title: ${it.title}")
            Log.d("FCM", "Notification Body: ${it.body}")
        }

        // Handle data payload
        remoteMessage.data.isNotEmpty().let {
            Log.d("FCM", "Data Payload: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            showNotification(it.title ?: "FCM", it.body ?: "Message received")
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String, message: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        val notificationBuilder = android.app.Notification.Builder(this, "default_channel_id")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_camera) // Replace with your app's icon
            .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Log the token or send it to your server
        Log.d("FCM", "New token: $token")
    }
}
