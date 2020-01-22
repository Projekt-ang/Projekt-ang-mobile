package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.apiclient.model.*
import com.example.myapplication.apiclient.service.Services
import kotlinx.android.synthetic.main.activity_exercise_selection.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


class ExerciseSelectionActivity : AppCompatActivity() {
    private var previousSearch = ""
    private var buttonsArray: Array<Button> = arrayOf()
    private var role: String? = "demo"
    var thisContext = this
    var openTab = "none"

    private fun getSentence(id: Int): Sentence? {
        var sentence: Sentence? = null
        val call: Call<Sentence> = Services.EXERCISE_SERVICE.getSentence(id)
        call.enqueue(object : Callback<Sentence> {
            override fun onResponse(call: Call<Sentence>, response: Response<Sentence>) {
                if (response.code() == 200) {
                    sentence = response.body()!!
                }
            }

            override fun onFailure(call: Call<Sentence>, t: Throwable) {
                println("-- Network error occured")
            }
        })
        return sentence
    }

    private fun getReadingVideoTest(id: Int): ReadingVideoTest? {
        var readingVideoTest: ReadingVideoTest? = null
        val call: Call<ReadingVideoTest> =
            Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTest(id)
        call.enqueue(object : Callback<ReadingVideoTest> {
            override fun onResponse(
                call: Call<ReadingVideoTest>,
                response: Response<ReadingVideoTest>
            ) {
                if (response.code() == 200) {
                    readingVideoTest = response.body()!!
                }
            }

            override fun onFailure(call: Call<ReadingVideoTest>, t: Throwable) {
                println("-- Network error occured")
            }
        })
        return readingVideoTest
    }

    private fun getGlossarie(id: Int): Glossarie? {
        var glossarie: Glossarie? = null
        val call: Call<Glossarie> = Services.EXERCISE_SERVICE.getGlossarie(id)
        call.enqueue(object : Callback<Glossarie> {
            override fun onResponse(call: Call<Glossarie>, response: Response<Glossarie>) {
                if (response.code() == 200) {
                    glossarie = response.body()!!
                }
            }

            override fun onFailure(call: Call<Glossarie>, t: Throwable) {
                println("-- Network error occured")
            }
        })
        return glossarie
    }


    private fun removeButtons(layout: LinearLayout, btArr: Array<Button>? = null) {
        for (button in this.buttonsArray) {
            layout.removeView(button)
        }
        buttonDemoExample.visibility = View.GONE
        this.buttonsArray = arrayOf()
    }

    //Creates list of buttons in a given view with given names.
    private fun createButtons(
        buttonNames: Array<String>,
        DestLinearLayout: LinearLayout,
        thisContext: Context
    ): Array<Button> {

        //array of created buttons. can be used to edit them.
        var buttonsArr: Array<Button>
        buttonsArr = arrayOf()
        //creates button item and adds it to a given view
        for (buttonName in buttonNames) {
            val newButton = Button(thisContext)
            newButton.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            newButton.text = buttonName
            newButton.setOnClickListener {
                Toast.makeText(thisContext, "Add Reading Test here.", Toast.LENGTH_SHORT).show()
            }
            DestLinearLayout.addView(newButton)
            buttonsArr = buttonsArr.plusElement(newButton)
        }
        this.buttonsArray = buttonsArr
        Toast.makeText(
            applicationContext,
            "Showing " + this.buttonsArray.size + " results",
            Toast.LENGTH_SHORT
        ).show()
        //returns Array of buttons for future refference i.e. button editing, etc, but the array is also added to global variable
        return buttonsArr
    }

    //switches view's visibility gicen a view and visibiliti to which and which from to change.
    private fun switchVisibility(layout: View, visibilityValue: Int) {
        if (layout.visibility == View.VISIBLE) {
            layout.visibility = visibilityValue
        } else layout.visibility = View.VISIBLE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_selection)



        if (intent.hasExtra("Role")) {
            this.role = intent.extras!!.getString("Role")!!

        } else {
            this.role = "demo1"
        }


        buttonReadingTestExercise.setOnClickListener {
            //DEMO BEHAVIOUR
//DEMO BEHAVIOUR
            if (this.openTab != "readingVideoTest") {
                if (this.role == "demo1") {
                    val demo_readingVideoTest_id: Int = 53
                    var readingVideoTest: ReadingVideoTest?
                    var readingVideoTests: Array<ReadingVideoTest>? = null
                    //remove buttons and create new ones
                    var btArr: Array<Button>
                    LinearLayoutTaskSelection.visibility = View.VISIBLE
                    this.removeButtons(LinearLayoutTaskSelection)
                    val text = exerciseSearchText.text
                    var buttonNames = Array(1) { "Demo" }
                    btArr = this.createButtons(
                        buttonNames,
                        LinearLayoutTaskSelection,
                        applicationContext
                    )
                    this.previousSearch = exerciseSearchText.text.toString()
                    for (i in btArr.indices) {
                        btArr[i].setOnClickListener() {
                            //ENVOKING DEMO ACTIVITY WITH INTENT demo
                        val call: Call<ReadingVideoTest> =
                            Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTest(demo_readingVideoTest_id)
                        call.enqueue(object : Callback<ReadingVideoTest> {
                            override fun onResponse(
                                call: Call<ReadingVideoTest>,
                                response: Response<ReadingVideoTest>
                            ) {
                                if (response.code() == 200) {
                                    readingVideoTest = response.body()!!
                                    //START ACTIVITY
                                    val intent =
                                        Intent(thisContext, ReadingWithTestActivity::class.java)
                                    intent.putExtra("readingVideoTest", readingVideoTest)
                                    thisContext.startActivity(intent)
                                }
                            }

                            override fun onFailure(call: Call<ReadingVideoTest>, t: Throwable) {
                                println("-- Network error occured")
                            }
                        })
                        }
                    }


                } else {
                    val demo_readingVideoTest_id: Int = 53
                    var readingVideoTest: ReadingVideoTest?
                    var readingVideoTests: Array<ReadingVideoTest>? = null
                    var buttonNames: Array<String> = emptyArray()

                    println("downloading ReadingVideoTests pages")

                    //GET FIRST PAGE OF RESULTS
                    val call: Call<ReadingVideoTestAllResponseEmbedded> =
                        Services.READING_VIDEO_TEST_SERVICE.getAll()
                    call.enqueue(object : Callback<ReadingVideoTestAllResponseEmbedded> {
                        override fun onResponse(
                            call: Call<ReadingVideoTestAllResponseEmbedded>,
                            response: Response<ReadingVideoTestAllResponseEmbedded>
                        ) {
                            if (response.code() == 200) {
                                println("Response body: " + response.body().toString())
                                readingVideoTests = response.body()!!.embedded!!.embedded
                                //CREATE BUTTONS FROM FIRST PAGE OF RESULTS
                                thisContext.removeButtons(LinearLayoutTaskSelection)

                                println("Deleted buttons")
                                for (i in readingVideoTests!!.indices) {
                                    println("Adding buttonName no. " + i.toString())
                                    buttonNames =
                                        buttonNames.plusElement(readingVideoTests!![i].name!!)
                                }
                                println("created buttonNames array")
                                thisContext.createButtons(
                                    buttonNames,
                                    LinearLayoutTaskSelection,
                                    applicationContext
                                )
                                println("created buttons")
                                for (i in thisContext.buttonsArray.indices) {
                                    thisContext.buttonsArray[i].setOnClickListener() {

                                        if (thisContext.role == "demo") {
                                            val call: Call<ReadingVideoTest> =
                                                Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTest(demo_readingVideoTest_id)
                                            call.enqueue(object : Callback<ReadingVideoTest> {
                                                override fun onResponse(
                                                    call: Call<ReadingVideoTest>,
                                                    response: Response<ReadingVideoTest>
                                                ) {
                                                    if (response.code() == 200) {
                                                        readingVideoTest = response.body()!!
                                                        //START ACTIVITY
                                                        val intent =
                                                            Intent(thisContext, ReadingWithTestActivity::class.java)
                                                        intent.putExtra("readingVideoTest", readingVideoTest)
                                                        thisContext.startActivity(intent)
                                                    }
                                                }

                                                override fun onFailure(call: Call<ReadingVideoTest>, t: Throwable) {
                                                    println("-- Network error occured")
                                                }
                                            })
                                        } else {
                                            readingVideoTest = readingVideoTests!![i]
                                            val intent =
                                                Intent(
                                                    thisContext,
                                                    ReadingWithTestActivity::class.java
                                                )
                                            intent.putExtra("readingVideoTest", readingVideoTest)
                                            thisContext.startActivity(intent)
                                        }
                                    }
                                }
                                LinearLayoutTaskSelection.visibility = View.VISIBLE
                                println("Successfully created buttons")
                            } else {
                                println("Response body: " + response.body().toString())
                            }
                        }

                        override fun onFailure(
                            call: Call<ReadingVideoTestAllResponseEmbedded>,
                            t: Throwable
                        ) {
                            println("-- Network error occurred" + t.toString())
                        }
                    })
                    println("Finished downloading ReadingVideoTests this many ->" + readingVideoTests?.size.toString() + " <- pages.")

                }
            this.openTab = "readingVideoTest"
            }else {
                LinearLayoutTaskSelection.visibility = View.INVISIBLE
                this.openTab = "none"
            }
        }

        SentencesExerciseButton.setOnClickListener {
            //DEMO BEHAVIOUR
            if (this.openTab != "sentences") {
                if (this.role == "demo1") {
                    val demo_sentence_id: Int = 255
                    //remove buttons and create new ones
                    var btArr: Array<Button>
                    LinearLayoutTaskSelection.visibility = View.VISIBLE
                    this.removeButtons(LinearLayoutTaskSelection)
                    val text = exerciseSearchText.text
                    var buttonNames = Array(1) { "Demo" }
                    btArr = this.createButtons(
                        buttonNames,
                        LinearLayoutTaskSelection,
                        applicationContext
                    )
                    this.previousSearch = exerciseSearchText.text.toString()
                    for (i in btArr.indices) {
                        btArr[i].setOnClickListener() {

                            var sentence: Sentence? = null
                            val call: Call<Sentence> = Services.EXERCISE_SERVICE.getSentence(demo_sentence_id)
                            call.enqueue(object : Callback<Sentence> {
                                override fun onResponse(call: Call<Sentence>, response: Response<Sentence>) {
                                    if (response.code() == 200) {
                                        sentence = response.body()!!
                                        //START ACTIVITY
                                        val intent =
                                            Intent(
                                                thisContext,
                                                SentenceActivity::class.java
                                            )
                                        intent.putExtra("sentence", sentence)
                                        intent.putExtra("demo", true)
                                        thisContext.startActivity(intent)
                                    }
                                }

                                override fun onFailure(call: Call<Sentence>, t: Throwable) {
                                    println("-- Network error occured")
                                }
                            })
                        }
                    }


                }else {
                    val demo_sentence_id: Int = 255
                    var sentence: Sentence? = null
                    var sentences: Array<Sentence>? = null
                    var buttonNames: Array<String> = emptyArray()

                    println("downloading Sentences pages")
                    val call: Call<SentenceAllResponseEmbedded> =
                        Services.EXERCISE_SERVICE.getAllSentences()
                    call.enqueue(object : Callback<SentenceAllResponseEmbedded> {
                        override fun onResponse(
                            call: Call<SentenceAllResponseEmbedded>,
                            response: Response<SentenceAllResponseEmbedded>
                        ) {
                            if (response.code() == 200) {
                                println("Response body: " + response.body().toString())
                                sentences = response.body()!!.embedded!!.embedded
                                //CREATE BUTTONS FROM FIRST PAGE OF RESULTS
                                thisContext.removeButtons(LinearLayoutTaskSelection)

                                println("Deleted buttons")
                                for (i in sentences!!.indices) {
                                    println("Adding buttonName no. " + i.toString())
                                    if (!sentences!![i].polishSentence.isNullOrBlank()) {
                                        buttonNames =
                                            buttonNames.plusElement(sentences!![i].polishSentence.toString())
                                    }
                                }
                                println("created buttonNames array")
                                thisContext.createButtons(
                                    buttonNames,
                                    LinearLayoutTaskSelection,
                                    applicationContext
                                )
                                println("created buttons")
                                for (i in thisContext.buttonsArray.indices) {
                                    thisContext.buttonsArray[i].setOnClickListener() {

                                        if (thisContext.role == "demo") {
                                            var sentence: Sentence? = null
                                            val call: Call<Sentence> = Services.EXERCISE_SERVICE.getSentence(demo_sentence_id)
                                            call.enqueue(object : Callback<Sentence> {
                                                override fun onResponse(call: Call<Sentence>, response: Response<Sentence>) {
                                                    if (response.code() == 200) {
                                                        sentence = response.body()!!
                                                        //START ACTIVITY
                                                        val intent =
                                                            Intent(
                                                                thisContext,
                                                                SentenceActivity::class.java
                                                            )
                                                        intent.putExtra("sentence", sentence)
                                                        thisContext.startActivity(intent)
                                                    }
                                                }

                                                override fun onFailure(call: Call<Sentence>, t: Throwable) {
                                                    println("-- Network error occured")
                                                }
                                            })
                                        } else {
                                            sentence = sentences!![i]
                                            val intent =
                                                Intent(
                                                    thisContext,
                                                    SentenceActivity::class.java
                                                )
                                            intent.putExtra("sentence", sentence)
                                            thisContext.startActivity(intent)
                                        }
                                    }
                                }
                                LinearLayoutTaskSelection.visibility = View.VISIBLE
                                println("Successfully created buttons")
                            } else {
                                println("Response body: " + response.body().toString())
                            }
                        }

                        override fun onFailure(
                            call: Call<SentenceAllResponseEmbedded>,
                            t: Throwable
                        ) {
                            println("-- Network error occurred" + t.toString())
                        }
                    })
                    println("Finished downloading Sentences this many ->" + sentences?.size.toString() + " <- pages.")

                }
                this.openTab = "sentences"
            }else {
                LinearLayoutTaskSelection.visibility = View.INVISIBLE
                this.openTab = "none"
            }
        }


        ExerciseGoToSearchButton.setOnClickListener {
            this.switchVisibility(LinearLayoutExerciseSearch, View.GONE)
        }

        FlashcardsExerciseButton.setOnClickListener {
            //DEMO BEHAVIOUR
            if (this.openTab != "glossaries") {
                if (this.role == "demo1") {
                    val demo_glossarie_id: Int = 463
                    //remove buttons and create new ones
                    var btArr: Array<Button>
                    LinearLayoutTaskSelection.visibility = View.VISIBLE
                    this.removeButtons(LinearLayoutTaskSelection)
                    val text = exerciseSearchText.text
                    var buttonNames = Array(1) { "Demo" }
                    btArr = this.createButtons(
                        buttonNames,
                        LinearLayoutTaskSelection,
                        applicationContext
                    )
                    this.previousSearch = exerciseSearchText.text.toString()
                    for (i in btArr.indices) {
                        btArr[i].setOnClickListener() {
                            //ENVOKING DEMO ACTIVITY WITH INTENT demo
                            var glossarie: Glossarie? = null
                            val call: Call<Glossarie> = Services.EXERCISE_SERVICE.getGlossarie(demo_glossarie_id)
                            call.enqueue(object : Callback<Glossarie> {
                                override fun onResponse(call: Call<Glossarie>, response: Response<Glossarie>) {
                                    if (response.code() == 200) {
                                        glossarie = response.body()!!
                                        //START ACTIVITY
                                        val intent =
                                            Intent(
                                                thisContext,
                                                FalshcardsActivity::class.java
                                            )
                                        intent.putExtra("glossarie", glossarie)
                                        intent.putExtra("demo", true)
                                        thisContext.startActivity(intent)
                                    }
                                }

                                override fun onFailure(call: Call<Glossarie>, t: Throwable) {
                                    println("-- Network error occured")
                                }
                            })
                        }
                    }


                } else {
                    val demo_glossarie_id: Int = 463
                    var glossarie: Glossarie? = null
                    var glossaries: Array<Glossarie>? = null
                    var buttonNames: Array<String> = emptyArray()

                    println("downloading Glossaries pages")
                    val call: Call<GlossarieAllResponseEmbedded> =
                        Services.EXERCISE_SERVICE.getAllGlossaries()
                    call.enqueue(object : Callback<GlossarieAllResponseEmbedded> {
                        override fun onResponse(
                            call: Call<GlossarieAllResponseEmbedded>,
                            response: Response<GlossarieAllResponseEmbedded>
                        ) {
                            if (response.code() == 200) {
                                println("Response body: " + response.body().toString())
                                glossaries = response.body()!!.embedded!!.embedded
                                //CREATE BUTTONS FROM FIRST PAGE OF RESULTS
                                thisContext.removeButtons(LinearLayoutTaskSelection)

                                println("Deleted buttons")
                                for (i in glossaries!!.indices) {
                                    println("Adding buttonName no. " + i.toString())
                                    if (!glossaries!![i].word.isNullOrBlank()) {
                                        buttonNames =
                                            buttonNames.plusElement(glossaries!![i].word.toString())
                                    }
                                }
                                println("created buttonNames array")
                                thisContext.createButtons(
                                    buttonNames,
                                    LinearLayoutTaskSelection,
                                    applicationContext
                                )
                                println("created buttons")
                                for (i in thisContext.buttonsArray.indices) {
                                    thisContext.buttonsArray[i].setOnClickListener() {

                                        if (thisContext.role == "demo") {
                                            var glossarie: Glossarie? = null
                                            val call: Call<Glossarie> = Services.EXERCISE_SERVICE.getGlossarie(demo_glossarie_id)
                                            call.enqueue(object : Callback<Glossarie> {
                                                override fun onResponse(call: Call<Glossarie>, response: Response<Glossarie>) {
                                                    if (response.code() == 200) {
                                                        glossarie = response.body()!!
                                                        //START ACTIVITY
                                                        val intent =
                                                            Intent(
                                                                thisContext,
                                                                FalshcardsActivity::class.java
                                                            )
                                                        intent.putExtra("glossarie", glossarie)
                                                        thisContext.startActivity(intent)
                                                    }
                                                }

                                                override fun onFailure(call: Call<Glossarie>, t: Throwable) {
                                                    println("-- Network error occured")
                                                }
                                            })

                                        } else {
                                            glossarie = glossaries!![i]
                                            val intent =
                                                Intent(
                                                    thisContext,
                                                    FalshcardsActivity::class.java
                                                )
                                            intent.putExtra("glossarie", glossarie)
                                            thisContext.startActivity(intent)
                                        }
                                    }
                                }
                                LinearLayoutTaskSelection.visibility = View.VISIBLE
                                println("Successfully created buttons")
                            } else {
                                println("Response body: " + response.body().toString())
                            }
                        }

                        override fun onFailure(
                            call: Call<GlossarieAllResponseEmbedded>,
                            t: Throwable
                        ) {
                            println("-- Network error occurred" + t.toString())
                        }
                    })
                    println("Finished downloading Glossaries this many ->" + glossaries?.size.toString() + " <- pages.")

                }
                this.openTab = "glossaries"
            } else {
                LinearLayoutTaskSelection.visibility = View.INVISIBLE
                this.openTab = "none"
            }
        
        }



        exerciseSearchButton.setOnClickListener {
            if (exerciseSearchText.text.toString() == "Search For Exercises by title..." || exerciseSearchText.text.toString() == this.previousSearch) Toast.makeText(
                applicationContext,
                "Write a search phrase first!",
                Toast.LENGTH_SHORT
            ).show()
            else {
                //remove buttons and create new ones
                LinearLayoutTaskSelection.visibility = View.VISIBLE
                this.removeButtons(LinearLayoutTaskSelection)
                val text = exerciseSearchText.text
                var buttonNames = Array(Random.nextInt(1, 5)) { i -> "$text " + (i + 1) }
                this.createButtons(buttonNames, LinearLayoutTaskSelection, applicationContext)
                this.previousSearch = exerciseSearchText.text.toString()
            }
        }

        buttonDemoExample.setOnClickListener {


        }
        //Creating buttons for Task Selection List
//        val buttonAmount = 15
//        var buttonNames = Array(buttonAmount) { i -> "TestButton no. $i" }
//        this.createButtons(buttonNames, LinearLayoutTaskSelection, applicationContext)


    }


}

