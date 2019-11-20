package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SentenceActivity : AppCompatActivity() {

    private val pressCounter = 0;
    private val maxPressCounter = 10;
    private val words: Array<String> = arrayOf("oranges", "Kamil", "likes");
    private val answer = "Kamil likes oranges";
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence)
    }
}