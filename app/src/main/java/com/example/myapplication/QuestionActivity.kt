package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.myapplication.apiclient.model.Question
import com.example.myapplication.apiclient.model.ReadingWithTest
import com.example.myapplication.apiclient.service.ReadingWithTestService
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    var currentQuestionCounter = 0
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val extras = intent.extras
        val readingWithTest = extras!!.getParcelable<ReadingWithTest>("readingWithTest")
        println(readingWithTest!!.questions[0].correctAnswer)

        val questions = readingWithTest.questions

        val initialList = ArrayList<String>()
        val mAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, initialList)
        listView.adapter = mAdapter

        textView.text = questions[currentQuestionCounter].text
        loadQuestion(questions, currentQuestionCounter, mAdapter)

        buttonNext.setOnClickListener {
            if (currentQuestionCounter < questions.size) {
                if (listView.selectedItemPosition == questions[currentQuestionCounter].correctAnswer) {
                    println("Good answer")
                    score++
                } else {
                    println("Bad answer")
                }
                println("ID = " + listView.selectedItemPosition )
                currentQuestionCounter++
                if (currentQuestionCounter < questions.size) {
                    textView.text = questions[currentQuestionCounter].text
                    loadQuestion(questions, currentQuestionCounter, mAdapter)
                }
            } else {
                textView.text = "Score"
                mAdapter.clear()
                mAdapter.add("You've got " + score + " points out of " + questions.size)
            }
        }

    }

    private fun loadQuestion(
        questions: Array<Question>,
        currentQuestionCounter: Int,
        mAdapter: ArrayAdapter<String>
    ) {
        mAdapter.clear()
        val question = questions[currentQuestionCounter]
        for (answer in question.answers) {
            mAdapter.add(answer)
        }
    }
}
