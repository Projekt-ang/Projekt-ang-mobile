package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise_selection.*
import android.content.Intent
import android.graphics.Color
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
        buttonDemoExample.visibility = View.GONE
        this.buttonsArray = arrayOf()
    }
//Creates list of buttons in a given view with given names.
    private fun createButtons(buttonNames: Array<String>, DestLinearLayout: LinearLayout, thisContext: Context): Array<Button> {

        //array of created buttons. can be used to edit them.
        var buttonsArr: Array<Button>
        buttonsArr = arrayOf()
        //creates button item and adds it to a given view
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
        //returns Array of buttons for future refference i.e. button editing, etc, but the array is also added to global variable
        return buttonsArr
    }
//switches view's visibility gicen a view and visibiliti to which and which from to change.
    private fun switchVisibility(layout: View, visibilityValue: Int){
        if (layout.visibility == View.VISIBLE){
            layout.visibility = visibilityValue
        }
        else layout.visibility = View.VISIBLE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_selection)


        buttonReadingTestExercise.setOnClickListener {
            this.removeButtons(LinearLayoutTaskSelection)
            buttonDemoExample.visibility = View.VISIBLE
            this.switchVisibility(LinearLayoutTaskSelection, View.GONE)
        }

        SentencesExerciseButton.setOnClickListener {
            //Creating buttons for Task Selection List
//            buttonReadingTestExercise.setBackgroundColor(Color.GRAY)
//            val buttonAmount = 6
//            var buttonNames = Array(buttonAmount) { i -> "Test Reading Quiz no. $i" }
//            this.createButtons(buttonNames, LinearLayoutTaskSelection, applicationContext)
        }


        ExerciseGoToSearchButton.setOnClickListener {
            this.switchVisibility(LinearLayoutExerciseSearch, View.GONE)
        }
        FlashcardsExerciseButton.setOnClickListener {
            val intent = Intent(this, FalshcardsActivity::class.java)
            this.startActivity(intent)
        }


        buttonDemoExample.setOnClickListener {
            val intent = Intent(this, ReadingWithTestActivity::class.java)
            this.startActivity(intent)
        }

        exerciseSearchButton.setOnClickListener {
            if (exerciseSearchText.text.toString() == "Search For Exercises by title..." || exerciseSearchText.text.toString() == this.previousSearch) Toast.makeText(applicationContext, "Write a search phrase first!", Toast.LENGTH_SHORT).show()
            else {
                //remove buttons and create new ones
                LinearLayoutTaskSelection.visibility = View.VISIBLE
                this.removeButtons(LinearLayoutTaskSelection)
                val text = exerciseSearchText.text
                var buttonNames = Array(Random.nextInt(1,5)) { i -> "$text " + (i+1) }
                this.createButtons(buttonNames, LinearLayoutTaskSelection, applicationContext)
                this.previousSearch = exerciseSearchText.text.toString()
            }
        }




    }


}

