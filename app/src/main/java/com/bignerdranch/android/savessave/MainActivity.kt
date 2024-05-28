package com.bignerdranch.android.savessave

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout)
        val launchCountTextView = findViewById<TextView>(R.id.launchCountTextView)

        val launchCount = preferences.getInt("launch_count", 0) + 1

        when {
            launchCount == 1 -> {
                mainLayout.setBackgroundColor(Color.RED)
                launchCountTextView.text = "Launch count: 1"
            }
            launchCount % 2 == 0 -> {
                mainLayout.setBackgroundColor(Color.GREEN)
                launchCountTextView.text = "Launch count: $launchCount"
            }
            else -> {
                mainLayout.setBackgroundColor(Color.BLUE)
                launchCountTextView.text = "Launch count: $launchCount"
            }
        }
        editor.putInt("launch_count", launchCount)
        editor.apply()
    }
}
