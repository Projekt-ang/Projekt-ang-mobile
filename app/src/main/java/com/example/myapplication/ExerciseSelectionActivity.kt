package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise_selection.*
import android.content.Intent
import android.view.DragAndDropPermissions
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random


class ExerciseSelectionActivity : AppCompatActivity() {

    private var previousSearch = ""

    private var buttonsArray: Array<Button> = arrayOf()

    private fun removeButtons(layout: LinearLayout){
        for (button in this.buttonsArray){
            layout.removeView(button)
        }
        this.buttonsArray = arrayOf()
    }

    private fun createButtons(buttonNames: Array<String>, DestLinearLayout: LinearLayout, thisContext: Context): Array<Button> {

        //array of created buttons. can be used to edit them.
        var buttonsArr: Array<Button>
        buttonsArr = arrayOf()

        for (buttonName in buttonNames){
            val newButton = Button(thisContext)
            newButton.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
            newButton.text = buttonName
            newButton.setOnClickListener {
                Toast.makeText(thisContext, "Add Reading Test here.", Toast.LENGTH_SHORT).show()
            }
            DestLinearLayout.addView(newButton)
            buttonsArr = buttonsArr.plusElement(newButton)
        }
        this.buttonsArray = buttonsArr
        Toast.makeText(applicationContext, "Showing " + this.buttonsArray.size + " results", Toast.LENGTH_SHORT).show()

        return buttonsArr
    }

    private fun switchVisibility(layout: View, visibilityValue: Int){
        if (layout.visibility == View.VISIBLE){
            layout.visibility = visibilityValue
        }
        else layout.visibility = View.VISIBLE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_selection)

        ReadingTestExerciseButton.setOnClickListener {
            this.switchVisibility(LinearLayoutTaskSelection, View.GONE)
        }

        SentencesExerciseButton.setOnClickListener {

        }


        ExerciseGoToSearchButton.setOnClickListener {
            this.switchVisibility(LinearLayoutExerciseSearch, View.GONE)
        }


        buttonDemoExample.setOnClickListener {
            val intent = Intent(this, ReadingWithTestActivity::class.java)
            this.startActivity(intent)
        }

        exerciseSearchButton.setOnClickListener {
            if (exerciseSearchText.text.toString() == "Search For Exercises by title..." || exerciseSearchText.text.toString() == this.previousSearch) Toast.makeText(applicationContext, "Write a search phrase first!", Toast.LENGTH_SHORT).show()
            else {
                this.removeButtons(LinearLayoutTaskSelection)
                val text = exerciseSearchText.text
                var buttonNames = Array(Random.nextInt(1,5)) { i -> "$text " + (i+1) }
                this.createButtons(buttonNames, LinearLayoutTaskSelection, applicationContext)
                this.previousSearch = exerciseSearchText.text.toString()
            }
        }

        //Creating buttons for Task Selection List
        val buttonAmount = 15
        var buttonNames = Array(buttonAmount) { i -> "TestButton no. $i" }
        this.createButtons(buttonNames, LinearLayoutTaskSelection, applicationContext)

        FlashcardsExerciseButton.setOnClickListener {
            this.removeButtons(LinearLayoutTaskSelection)
            Toast.makeText(applicationContext, "buttonsArray now has " + this.buttonsArray.size + " buttons", Toast.LENGTH_SHORT).show()
        }

    }


}

