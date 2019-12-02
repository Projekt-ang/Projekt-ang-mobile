package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import java.util.*

class SentenceActivity : AppCompatActivity() {

    private var pressCounter = 0
    private val maxPressCounter = 10
    private var keys = arrayOf("oranges ", "Kamil ", "much ", "very ", "likes ")
    private val answer = "Kamil likes oranges very much"
    private lateinit var sentencesActivityHeader: TextView
    private lateinit var sentenceActivityQuestion: TextView
    private lateinit var yourWords: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence)

        keys = shuffleArray(keys)

        for (key in keys) {
            addView(
                findViewById(R.id.LayoutParent), key,
                findViewById(R.id.EditText)
            )
        }
    }

    private fun shuffleArray(ar: Array<String>): Array<String> {
        val rnd = Random()
        for (i in ar.size - 1 downTo 1) {
            val index = rnd.nextInt(i + 1)
            val a = ar[index]
            ar[index] = ar[i]
            ar[i] = a
        }
        return ar
    }

    private fun addView(viewParent: LinearLayout, text: String, editText: EditText) {
        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayoutParams.rightMargin = 20

        val textView = TextView(this)

        textView.layoutParams = linearLayoutParams
        textView.background = AppCompatResources.getDrawable(this, R.drawable.bgpink)
        ContextCompat.getColor(this, R.color.colorAccent)
        textView.gravity = Gravity.CENTER
        textView.text = text
        textView.isClickable = true
        textView.isFocusable = true
        textView.textSize = 24f

        sentencesActivityHeader = findViewById(R.id.sentencesActivityHeader)
        sentenceActivityQuestion = findViewById(R.id.sentenceActivityQuestion)
        yourWords = findViewById(R.id.yourWords)

        textView.setOnClickListener {
            if (pressCounter < maxPressCounter) {
                if (pressCounter == 0)
                    editText.setText("")

                editText.setText(editText.text.toString() + text)
                // miejsce na animacje
                textView.animate().alpha(0f).duration = 300
                pressCounter++

                if (pressCounter == maxPressCounter)
                    doValidate()


            }
        }

        viewParent.addView(textView)

    }

    private fun doValidate() {
        pressCounter = 0
        val editText = findViewById<EditText>(R.id.EditText)
        val linearLayout = findViewById<LinearLayout>(R.id.LayoutParent)

        if (editText.text.toString() == answer) {
            editText.setText("")
        } else {
            editText.setText("")
        }

        keys = shuffleArray(keys)
        linearLayout.removeAllViews()

        for (key in keys) {
            addView(linearLayout, key, editText)
        }
    }


}