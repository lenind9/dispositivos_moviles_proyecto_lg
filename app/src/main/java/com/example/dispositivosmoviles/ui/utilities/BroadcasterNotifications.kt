package com.example.dispositivosmoviles.ui.utilities

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.ui.activities.NotificationActivity

class BroadcasterNotifications: BroadcastReceiver (){

    val CHANNEL: String = "Notificaciones"

    override fun onReceive(context: Context, intent: Intent?) {

        val myIntent = Intent(context, NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val myPendingIntent = PendingIntent.getActivity(
            context,
            0,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val noti = NotificationCompat.Builder(context, CHANNEL)
        noti.setContentTitle("Broadcaster notificacion")
        noti.setContentText("Tienes una notificacion postergada")
        noti.setSmallIcon(R.drawable.baseline_favorite_24)
        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Esta es una notificacion para recordar que estamos trabajando en Android")
        )
        noti.setContentIntent(myPendingIntent)
        noti.setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        notificationManager.notify(1, noti.build())

    }
}