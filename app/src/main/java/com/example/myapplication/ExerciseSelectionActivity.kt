package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise_selection.*
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout




class ExerciseSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_selection)

        ReadingTestExcerciseButton.setOnClickListener {
            val intent = Intent(this, ReadingWithTestActivity::class.java)
            startActivity(intent)
        }

        SentencesExcerciseButton.setOnClickListener {

        }

        FlashcardsExcerciseButton.setOnClickListener {

        }
    }

}
