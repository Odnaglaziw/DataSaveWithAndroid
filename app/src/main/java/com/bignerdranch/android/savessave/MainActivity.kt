package com.bignerdranch.android.savessave

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bignerdranch.android.savessave.ui.theme.SavesSaveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        // Получаем количество запусков
        val launchCount = preferences.getInt("launch_count", 0) + 1

        if (launchCount != 0 && launchCount % 2 == 0) {
            editor.putInt("launch_count", launchCount)
            findViewById<TextView>(R.id.launchCountTextView).text = "Launch count: $launchCount"
        } else if (launchCount != 1) {
            editor.putInt("launch_count", 0)
            findViewById<TextView>(R.id.launchCountTextView).text = "Launch count: 0"
        } else{
            editor.putInt("launch_count", launchCount)
            findViewById<TextView>(R.id.launchCountTextView).text = "Launch count: 1"
        }

        editor.apply()
    }
}
