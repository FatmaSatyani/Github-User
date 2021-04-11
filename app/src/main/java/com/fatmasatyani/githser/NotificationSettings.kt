package com.fatmasatyani.githser

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.fatmasatyani.githser.alarm.AlarmReceiver

class NotificationSettings : AppCompatActivity (){

    companion object {
        const val PREFS = "Setting"
        private const val DAILY = "daily"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchButton : Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reminder_settings)
        switchButton = findViewById(R.id.switchButton)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Settings"

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        setSwitch()

            switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setDailyReminder(
                    this, AlarmReceiver.TYPE_DAILY, "Let's find your favorite on Githser"
                )
            } else {
                alarmReceiver.cancelAlarm(this)
            }
            saveChange(isChecked)
        }
    }

    private fun setSwitch() {
        switchButton.isChecked = mSharedPreferences.getBoolean(DAILY, false)
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY,value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}