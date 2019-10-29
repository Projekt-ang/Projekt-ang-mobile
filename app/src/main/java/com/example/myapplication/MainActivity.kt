package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

private val ADD_TASK_REQUEST = 1


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        var loginButton : Button = findViewById(R.id.loginButton)
        val demoButton : Button = findViewById(R.id.demoButton)

        loginButton.setOnClickListener {
            println("test")
        }

        demoButton.setOnClickListener {
            val intent = Intent(this, ExerciseSelectionActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }
    }
}
