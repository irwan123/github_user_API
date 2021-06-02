package com.example.githubuserapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuserapi.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reminderPreference = ReminderPreference(this)
        if (reminderPreference.getReminder().reminder){
            binding.switchAlarm.isChecked = true
        } else{
            binding.switchAlarm.isChecked = false
        }

        alarmReceiver = AlarmReceiver()
        binding.switchAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                saveReminder(true)
                alarmReceiver.setRepeatAlarm(this,"RepeatAlarm","09:00", "GithubUserAPI Alarm")
            } else{
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }
    private fun saveReminder(state: Boolean){
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()
        reminder.reminder = state
        reminderPreference.setReminder(reminder)
    }
}