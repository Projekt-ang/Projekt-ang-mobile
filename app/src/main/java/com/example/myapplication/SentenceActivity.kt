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
import com.example.myapplication.apiclient.model.Sentence
import com.example.myapplication.apiclient.service.Services
import com.nex3z.flowlayout.FlowLayout
import kotlinx.android.synthetic.main.activity_sentence.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*



class SentenceActivity : AppCompatActivity() {





    private var pressCounter = 0
    private val maxPressCounter = 10
    var polish = "Kamil bardzo lubi pomarańcze"

    var keysData = "Kamil likes oranges very much"


//    var sentence = (0..keysData.size).random()
//    var new = keysData[sentence]

    fun mainSplit(args: Array<String>) {
        var str = keysData
        var delimeter = " "

        var parts = str.split(delimeter)
    }

    var new = keysData.split(" ").toTypedArray()

    var keys = shuffleArray(new)

    //    private val answer = "Kamil likes oranges very much"
    private lateinit var sentencesActivityHeader: TextView
    private lateinit var yourWords: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence)

//        var sentence : Sentence? = null
//        if (intent.hasExtra("sentence")) {
//            sentence = intent.extras!!.getParcelable("sentence")
//            if (sentence != null) {
//                polish = sentence!!.polishSentence.toString()
//                keysData = sentence!!.englishSentence.toString()
//            }
//        }


        var sentences : Sentence?
        if (intent.hasExtra("sentences")) {
            sentences = intent.extras!!.getParcelable("sentences")!!
            if (sentences != null) {
                polish = sentences!!.polishSentence.toString()
                keysData = sentences!!.englishSentence.toString()
            }else{
                polish = "--"
                keysData = "--"
            }
        } else {
            val call: Call<Sentence> = Services.EXERCISE_SERVICE.getSentence(1)
            call.enqueue(object : Callback<Sentence> {
                override fun onResponse(call: Call<Sentence>, response: Response<Sentence>) {
                    if (response.code() == 200) {
                        sentences = response.body()!!
                        if (sentences != null) {
                            polish = sentences!!.polishSentence.toString()
                            keysData = sentences!!.englishSentence.toString()
                        }else{
                            polish = "--"
                            keysData = "--"
                        }
                    }
                }
                override fun onFailure(call: Call<Sentence>, t: Throwable) {
                    println("-- Network error occured, code: " + t.toString())
                }
            })
        }


        for (key in keys) {
            addView(
                layoutParent, key,
                findViewById(R.id.editText)
            )

        }
    }

    fun shuffleArray(ar: Array<String>): Array<String> {
        val rnd = Random()
        for (i in ar.size - 1 downTo 1) {
            val index = rnd.nextInt(i + 1)
            val a = ar[index]
            ar[index] = ar[i]
            ar[i] = a
        }
        return ar
    }

    fun addView(viewParent: FlowLayout, text: String, editText: EditText) {
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
        yourWords = findViewById(R.id.yourWords)

        textView.setOnClickListener {
            if (pressCounter < maxPressCounter) {
                if (pressCounter == 0)
                    editText.setText("")

                editText.setText(editText.text.toString() + text.plus(" "))

                // miejsce na animacje
                textView.animate().alpha(0f).duration = 300
                pressCounter++

                if (pressCounter == new.size) {
                    doValidate()
                }
            }
        }
        viewParent.addView(textView)
    }


    /*
    private fun reverseView(viewParent: FlowLayout, questions: String) {
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
        textView.questions = questions
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
    fun doValidate() {
        pressCounter = 0
        val editText = findViewById<EditText>(R.id.editText)
        val flowLayout = layoutParent

//        var keysData2 = keysData.trim()

        var keysData2 = keysData.replace("\\s".toRegex(), "")

        if (editText.text.toString() == keysData2) {
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