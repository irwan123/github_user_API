package com.example.githubuserapi

import android.content.Context

class ReminderPreference(context: Context) {
    companion object{
        const val PREF_NAME = "reminder_pref"
        private const val REMINDER = "isReminder"
    }
    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    fun setReminder(value: Reminder){
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.reminder)
        editor.apply()
    }
    fun getReminder(): Reminder{
        val model = Reminder()
        model.reminder = preference.getBoolean(REMINDER, false)
        return model
    }
}