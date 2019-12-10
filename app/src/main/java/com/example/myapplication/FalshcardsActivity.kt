package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_falshcards.*

class FalshcardsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_falshcards)


        FlashcardsButtonMain.setOnClickListener(){
            if (FlashcardsButtonMain.text == "Rhino") FlashcardsButtonMain.text = "Nosorożec"
            else FlashcardsButtonMain.text = "Rhino"
        }
    }
}
