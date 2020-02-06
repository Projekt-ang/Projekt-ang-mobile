package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.apiclient.model.*
import com.example.myapplication.apiclient.service.Services
import kotlinx.android.synthetic.main.activity_question.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val readingVideoTest = intent.extras!!.getParcelable<ReadingVideoTest>("readingVideoTest")

        var questions : Array<Question>
        val call: Call<QuestionsResponseEmbedded> = Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTestQuestions(
            readingVideoTest!!.id)
        call.enqueue(object : Callback<QuestionsResponseEmbedded> {
            override fun onResponse(call: Call<QuestionsResponseEmbedded>, response: Response<QuestionsResponseEmbedded>) {
                if (response.code() == 200) {
                    questions = response.body()!!.embedded!!.questions!!
                    dynamicQuestions(readingVideoTest, questions)
                }
            }
            override fun onFailure(call: Call<QuestionsResponseEmbedded>, t: Throwable) {
                println("-- Network error occured" + t.message)
            }
        })
    }

    private fun dynamicQuestions(readingVideoTest: ReadingVideoTest, questions: Array<Question>) {
        val questionsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())
        listView.adapter = questionsAdapter

        var selectedItem = -1
        var currentQuestionCounter = 0
        var answers: ArrayList<Answers> = ArrayList<Answers>()
        val userAnswers = arrayOfNulls<Int>(questions.size)

        for (q in questions) {
            val id = q.id
            val callAnswers: Call<AnswersResponseEmbedded> = Services.QUESTION_SERVICE.getAnswers(id)
            callAnswers.enqueue(object : Callback<AnswersResponseEmbedded> {
                override fun onResponse(call: Call<AnswersResponseEmbedded>, response: Response<AnswersResponseEmbedded>) {
                    if (response.code() == 200) {
                        val answer = response.body()!!.embedded!!
                        answers.add(answer)
                    }
                }
                override fun onFailure(call: Call<AnswersResponseEmbedded>, t: Throwable) {
                    println("-- Network error occured" + t.message)
                }
            })
        }

        textView.text = questions[currentQuestionCounter].text
        loadQuestion(questions, currentQuestionCounter, questionsAdapter, userAnswers)

        listView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            selectedItem = i
        }

        buttonBack.setOnClickListener {
            if (currentQuestionCounter == 0) {
                AlertDialog.Builder(this)
                    .setMessage("Do you want to get back? All your current progress will be lost")
                    .setPositiveButton(getString(R.string.yes)) { arg0, arg1 ->
                        finish()
                    }
                    .setNegativeButton(getString(R.string.no)) { arg0, arg1 -> }
                    .show()
            } else {
                if (selectedItem != -1)
                    userAnswers[currentQuestionCounter] = selectedItem
                currentQuestionCounter--
                selectedItem = loadQuestion(
                    questions,
                    currentQuestionCounter,
                    questionsAdapter,
                    userAnswers
                )
            }
        }
        buttonLookup.setOnClickListener {
            var lastSelected = -1
            if (selectedItem != -1)
                lastSelected = selectedItem

            val intent = Intent(this, ReadingWithTestActivity::class.java)
            intent.putExtra("lookup",true)
            intent.putExtra("readingVideoTest",readingVideoTest)
            startActivity(intent)
            if (lastSelected != -1) {
                selectedItem = lastSelected
                performClick(listView, selectedItem)
            }
        }
        buttonNext.setOnClickListener {
            if (currentQuestionCounter == questions.size - 1) {
                userAnswers[currentQuestionCounter] = selectedItem
                AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setPositiveButton(getString(R.string.yes)) { arg0, arg1 ->
                        val correctAnswers = calculateScore(answers, userAnswers)
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra("correctAnswers", correctAnswers)
                        intent.putExtra("questionsSize", questions.size)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton(getString(R.string.no)) { arg0, arg1 -> }
                    .show()
            } else {
                if (selectedItem != -1)
                    userAnswers[currentQuestionCounter] = selectedItem
                currentQuestionCounter++
                selectedItem = loadQuestion(questions, currentQuestionCounter, questionsAdapter, userAnswers)
            }
        }
    }

    private fun calculateScore(questions: ArrayList<Answers>, userAnswers: Array<Int?>): Int {
        var score = 0
        for (i in questions.indices) {
            val selectedAnswer = userAnswers[i]
            if (selectedAnswer != null && selectedAnswer != -1)
                if (questions[i].answers!![selectedAnswer].correct!!)
                  score++
        }

        return score
    }

    private fun loadQuestion(
        questions: Array<Question>?,
        currentQuestionCounter: Int,
        mAdapter: ArrayAdapter<String>,
        userAnswers: Array<Int?>
    ): Int {
        progressBar.progress = (currentQuestionCounter) * 100 / (questions!!.size)
        textView.text = questions[currentQuestionCounter].text

        mAdapter.clear()
        val question = questions[currentQuestionCounter]
        //for (answer in question.answers!!) mAdapter.add(answer.text)

        val call: Call<AnswersResponseEmbedded> = Services.QUESTION_SERVICE.getAnswers(questions[currentQuestionCounter].id)
        call.enqueue(object : Callback<AnswersResponseEmbedded> {
            override fun onResponse(call: Call<AnswersResponseEmbedded>, response: Response<AnswersResponseEmbedded>) {
                if (response.code() == 200) {
                    val answers = response.body()!!.embedded!!.answers!!
                    answers.forEach { a ->
                        mAdapter.add(a.text)
                    }
                }
            }
            override fun onFailure(call: Call<AnswersResponseEmbedded>, t: Throwable) {
                println("-- Network error occured" + t.message)
            }
        })

        listView.clearChoices()
        listView.adapter = mAdapter

        var currentSel = -1;

        if (userAnswers[currentQuestionCounter] != null && userAnswers[currentQuestionCounter] != -1) {
            val position = userAnswers[currentQuestionCounter]!!
            //performClick(listView, position)
            currentSel = position
        }
        if (buttonNext.text == getString(R.string.finish)) {
            buttonNext.text = getString(R.string.next)
        }
        if (currentQuestionCounter >= questions.size - 1) {
            buttonNext.text = getString(R.string.finish)
        }
        return currentSel
    }

    private fun performClick(listView: ListView, position: Int) {
        listView.requestFocusFromTouch()
        listView.setSelection(position)

        listView.performItemClick(
            listView.adapter.getView(position, null, null), position, position.toLong()
        )
    }

    override fun onStop() {
        setResult(2)
        super.onStop()
    }

    override fun onDestroy() {
        setResult(2)
        super.onDestroy()
    }
}
