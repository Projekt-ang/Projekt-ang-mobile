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

        val questionsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())
        listView.adapter = questionsAdapter

        var selectedItem = -1
        var currentQuestionCounter = 0
        val userAnswers = arrayOfNulls<Int>(questions.size)

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
            intent.putExtra("readingWithTest",readingWithTest)
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
                selectedItem = loadQuestion(questions, currentQuestionCounter, questionsAdapter, userAnswers)
            }
        }
    }

    private fun calculateScore(questions: Array<Question>, userAnswers: Array<Int?>): Int {
        var score = 0
        for (i in questions.indices) {
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
    ): Int {
        progressBar.progress = (currentQuestionCounter) * 100 / (questions.size)
        textView.text = questions[currentQuestionCounter].text

        mAdapter.clear()
        val question = questions[currentQuestionCounter]
        for (answer in question.answers) mAdapter.add(answer)

        listView.clearChoices()
        listView.adapter = mAdapter

        var currentSel = -1;

        if (userAnswers[currentQuestionCounter] != null) {
            val position = userAnswers[currentQuestionCounter]!!
            performClick(listView, position)
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
}
