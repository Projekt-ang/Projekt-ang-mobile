package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_excercise_selection.*


class ExcerciseSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise_selection)

        ReadingTestExcerciseButton.setOnClickListener {

        }

        SentencesExcerciseButton.setOnClickListener {

        }

        FlashcardsExcerciseButton.setOnClickListener {

        }
    }

}
