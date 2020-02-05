package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
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
    //    private val maxPressCounter = 10
    private var polish = "Marta bardzo lubi pomarańcze"
    private var keysData = "Kamil likes oranges very much"
    var txt = ""

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
    private lateinit var sentenceActivityQuesstion: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence)


        var sentences : Sentence?
        if (intent.hasExtra("sentence")) {
            sentences = intent.extras!!.getParcelable("sentence")!!
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

        var txt = findViewById<TextView>(R.id.sentenceActivityQuestion)
        txt.text = polish

        new = keysData.split(" ").toTypedArray()
        var keys = shuffleArray(new)

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


    fun addView(viewParent: FlowLayout, text: String, editText: FlowLayout) {
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
        textView.setPadding(32,16,32,16)
        textView.text = text
        textView.isClickable = false
        textView.isFocusable = false
        textView.textSize = 24f

        sentencesActivityHeader = findViewById(R.id.sentencesActivityHeader)
        yourWords = findViewById(R.id.yourWords)

        textView.setOnClickListener {
            if (pressCounter < keys.size) {
                addView(editText, text, findViewById(R.id.editText))


                txt += text

//                var toast = Toast.makeText(this, txt, Toast.LENGTH_SHORT)
//                toast.show()

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

    @SuppressLint("WrongConstant")
    fun doValidate() {



        pressCounter = 0
        val editText = findViewById<FlowLayout>(R.id.editText)
        val flowLayout = layoutParent

//        var keysData2 = keysData.trim()

        var keysData2 = keysData.replace("\\s".toRegex(), "")
//
        var keysCheck = txt
//        var keysCheck2 = keysCheck.replace("\\s".toRegex(), "")



        if (keysCheck == keysData2) {

            val toast = Toast.makeText(this, "Dobrze!", 1000)
            toast.show()
            finish()
        } else {

            val toast = Toast.makeText(this, "Żle!", 1000)
            toast.show()
            finish()
        }
        keys = shuffleArray(keys)
        flowLayout.removeAllViews()

    }
}