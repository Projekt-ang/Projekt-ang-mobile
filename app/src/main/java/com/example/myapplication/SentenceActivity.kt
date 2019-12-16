package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.myapplication.R.id.LayoutParent
import com.nex3z.flowlayout.FlowLayout
import java.util.*

class SentenceActivity : AppCompatActivity() {

    private var pressCounter = 0
    private val maxPressCounter = 5
    private var keys = arrayOf("oranges ", "Kamil ", "much", "very ", "likes ")
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
                findViewById(LayoutParent), key,
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

    private fun addView(viewParent: FlowLayout, text: String, editText: EditText) {
        val flowLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        flowLayoutParams.rightMargin = 20

        val textView = TextView(this)

        textView.layoutParams = flowLayoutParams
        textView.background = AppCompatResources.getDrawable(this, R.drawable.border_sentence)
        ContextCompat.getColor(this, R.color.colorLight)
        textView.gravity = Gravity.NO_GRAVITY
        textView.setPadding(36,16,36,16)
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

    /*
    private fun reverseView(viewParent: FlowLayout, text: String) {
        val flowLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        flowLayoutParams.rightMargin = 20

        val textView = TextView(this)

        textView.layoutParams = flowLayoutParams
        textView.background = AppCompatResources.getDrawable(this, R.drawable.border_sentence)
        ContextCompat.getColor(this, R.color.colorGray)
        textView.gravity = Gravity.NO_GRAVITY
        textView.setPadding(36,16,36,16)
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

                // miejsce na animacje
                textView.animate().alpha(0f).duration = 300
                pressCounter++

                // miejsce na animacje
                textView.animate().alpha(0f).duration = 300
                pressCounter++

                if (pressCounter == maxPressCounter)
                    doValidate()


            }
        }
    }
    */




    @SuppressLint("WrongConstant")
    private fun doValidate() {
        pressCounter = 0
        val editText = findViewById<EditText>(R.id.EditText)
        val flowLayout = findViewById<FlowLayout>(LayoutParent)

        if (editText.text.toString() == answer) {
            editText.setText("")
            val toast = Toast.makeText(this, "Dobrze!", 1000)
            toast.show()
        } else {
            editText.setText("")
            val toast = Toast.makeText(this, "Żle!", 1000)
            toast.show()
        }

        keys = shuffleArray(keys)
        flowLayout.removeAllViews()

        for (key in keys) {
            addView(flowLayout, key, editText)
        }


    }


}