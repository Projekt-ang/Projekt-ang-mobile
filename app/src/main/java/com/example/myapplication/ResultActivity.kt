package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.apiclient.model.ReadingWithTest
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val correctAnswers = intent.getIntExtra("correctAnswers", 1)
        val questionsSize = intent.getIntExtra("questions", 1)

        progressBar2.progress = correctAnswers / questionsSize * 100
        textView3.text = "Udzielono $correctAnswers poprawnych odpowiedzi na $questionsSize"

    }
}
