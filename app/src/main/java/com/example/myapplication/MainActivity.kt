package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

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
    }
}
