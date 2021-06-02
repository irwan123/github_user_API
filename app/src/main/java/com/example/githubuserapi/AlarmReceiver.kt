package com.example.githubuserapi

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.lang.StringBuilder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    companion object{
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "Reminder Alarm GithubUserAPI"
        private const val FORMAT_TIME = "HH:mm:ss"
        private const val REPEAT_ID = 101
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
       sendNotification(context)
    }
    private fun sendNotification(context: Context){
        val intentPackage = context?.packageManager.getLaunchIntentForPackage("com.example.githubuserapi")
        val pendingIntent = PendingIntent.getActivity(context, 0, intentPackage, 0)
        val notificationSettings = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(context.resources.getString((R.string.app_name)))
            .setContentText("Search Your User Favorite")
            .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(CHANNEL_ID)
            notificationSettings.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationSettings.notify(NOTIFICATION_ID, notification)
    }
    fun setRepeatAlarm(context: Context, type: String, time: String, message: String){
        if (isDateInvalid(time, FORMAT_TIME)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_ID, intent,0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context,"Setup Alarm", Toast.LENGTH_SHORT).show()
    }
    private fun isDateInvalid(time: String, formatTime: String): Boolean{
        return try {
            val df = SimpleDateFormat(formatTime, Locale.getDefault())
            df.isLenient = false
            df.parse(time)
            false
        } catch (e:ParseException){
            true
        }
    }
    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)
        val requestCode = REPEAT_ID
        val pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context,"Alarm Canceled", Toast.LENGTH_SHORT).show()
    }
}
