package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.myapplication.apiclient.model.Question
import com.example.myapplication.apiclient.model.ReadingWithTest
import kotlinx.android.synthetic.main.activity_question.*
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ListView


class QuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val readingWithTest = intent.extras!!.getParcelable<ReadingWithTest>("readingWithTest")
        val questions = readingWithTest!!.questions

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())
        listView.adapter = adapter

        var currentQuestionCounter = 0
        var score = 0

        val userAnswers = arrayOfNulls<Int>(questions.size)

        textView.text = questions[currentQuestionCounter].text
        loadQuestion(questions, currentQuestionCounter, adapter, userAnswers)

        var selectedItem = -1

        listView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            selectedItem = i
        }

        buttonBack.setOnClickListener {
            if (currentQuestionCounter == 0) {
                AlertDialog.Builder(this)
                    .setMessage("Czy chcesz powrócić do treści zadania i przerwać quiz? Dotychczasowy postęp zostanie utrcony.")
                    .setTitle("Powrót")
                    .setPositiveButton(getString(R.string.yes)) { arg0, arg1 ->
                        finish()
                    }
                    .setNegativeButton(getString(R.string.no)) { arg0, arg1 -> }
                    .show()
            } else {
                if (selectedItem != -1)
                    userAnswers[currentQuestionCounter] = selectedItem
                currentQuestionCounter--
                loadQuestion(questions, currentQuestionCounter, adapter, userAnswers)
            }
        }
        buttonNext.setOnClickListener {
            if (currentQuestionCounter == questions.size - 1) {
                userAnswers[currentQuestionCounter] = selectedItem
                AlertDialog.Builder(this)
                    .setTitle("Czy jesteś pewny swoich odpowiedzi?")
                    .setPositiveButton(getString(R.string.yes)) { arg0, arg1 ->
                        val correctAnswers = calculateScore(questions, userAnswers)
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra("correctAnswers", correctAnswers)
                        intent.putExtra("questions", questions.size)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton(getString(R.string.no)) { arg0, arg1 -> }
                    .show()
            } else {
                if (selectedItem != -1)
                    userAnswers[currentQuestionCounter] = selectedItem
                currentQuestionCounter++
                loadQuestion(questions, currentQuestionCounter, adapter, userAnswers)
            }
        }
    }

    private fun calculateScore(questions: Array<Question>, userAnswers: Array<Int?>): Int {
        var score = 0
        for (i in 0 until questions.size) {
            if (userAnswers[i] == questions[i].correctAnswer)
                score++
        }
        return score
    }

    private fun loadQuestion(
        questions: Array<Question>,
        currentQuestionCounter: Int,
        mAdapter: ArrayAdapter<String>,
        userAnswers: Array<Int?>
    ) {
        progressBar.progress = (currentQuestionCounter) * 100 / (questions.size)
        textView.text = questions[currentQuestionCounter].text
        mAdapter.clear()
        listView.clearChoices()
        listView.adapter = mAdapter

        val question = questions[currentQuestionCounter]
        for (answer in question.answers) {
            mAdapter.add(answer)
        }
        if (userAnswers[currentQuestionCounter] != null) {
           val position = userAnswers[currentQuestionCounter]!!
            listView.requestFocusFromTouch()
            listView.setSelection(position)

            listView.performItemClick(
                listView.getAdapter().getView(position,null,null),position, position.toLong()
            )
        }
    }
}
