package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityNotificationBinding
import com.example.dispositivosmoviles.ui.utilities.BroadcasterNotifications
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotification.setOnClickListener {
            createNotificationChannel()
            sendNotification()
        }

        binding.btnNotificationProgramada.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hora = binding.timePicker.hour
            val minutes = binding.timePicker.minute

            Toast.makeText(this, "La notificacion se activara a las: $hora con $minutes", Toast.LENGTH_SHORT).show()
            calendar.set(Calendar.HOUR, hora)
            calendar.set(Calendar.MINUTE, minutes)
            calendar.set(Calendar.SECOND, 0)

            sendNotificationTimePicker(calendar.timeInMillis)
        }
    }

    private fun sendNotificationTimePicker(time: Long) {
        val myIntent = Intent(applicationContext, BroadcasterNotifications::class.java) //applicationContext: desde cualquier parte de la app
        val myPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            1,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT //formas de mostrar lo que va a pasar cuando se abra el intent
            )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, myPendingIntent)
    }

    val CHANNEL : String = "Notificaciones"

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Variedades"
            val descriptionText = "Notificaciones simples de variedades"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotification() {

        val intent = Intent(this, CameraActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        //crea la notificacion
        val noti = NotificationCompat.Builder(this, CHANNEL)
        noti.setContentTitle("Primera notificacion")
        noti.setContentText("Tienes una notificacion")
        noti.setSmallIcon(R.drawable.twitter)
        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(NotificationCompat.BigTextStyle()
            .bigText("Esta es una notificacion para recordar que estamos trabajando en Android"))
        noti.setContentIntent(pendingIntent)
        noti.setAutoCancel(true)

        //envia la notificacion
        with(NotificationManagerCompat.from(this)){
            notify(1, noti.build())
        }
    }
}