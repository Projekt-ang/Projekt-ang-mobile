package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val correctAnswers = intent.getIntExtra("correctAnswers", 1)
        val questionsSize = intent.getIntExtra("questionBackups", 1)

        progressBar2.progress = correctAnswers * 100 / questionsSize
        if (correctAnswers == 1)
            textView3.text = "You scored 1 point out of the $questionsSize points"
        else
            textView3.text = "You scored $correctAnswers points out of the $questionsSize points"

    }
}
