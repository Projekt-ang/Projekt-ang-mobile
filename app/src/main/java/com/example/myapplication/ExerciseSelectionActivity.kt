package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
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
    var buttonComplexArray: Array<ButtonComplex> = arrayOf()
    var newButtonComplexArray: Array<ButtonComplex> = arrayOf()
    private var role: String? = "demo"
    private var enableRole : Boolean = true
    var thisContext = this
    var openTab = "none"

//    private fun getSentence(id: Int): Sentence? {
//        var sentence: Sentence? = null
//        val call: Call<Sentence> = Services.EXERCISE_SERVICE.getSentence(id)
//        call.enqueue(object : Callback<Sentence> {
//            override fun onResponse(call: Call<Sentence>, response: Response<Sentence>) {
//                if (response.code() == 200) {
//                    sentence = response.body()!!
//                }
//            }
//
//            override fun onFailure(call: Call<Sentence>, t: Throwable) {
//                println("-- Network error occured")
//            }
//        })
//        return sentence
//    }
//
//    private fun getReadingVideoTest(id: Int): ReadingVideoTest? {
//        var readingVideoTest: ReadingVideoTest? = null
//        val call: Call<ReadingVideoTest> =
//            Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTest(id)
//        call.enqueue(object : Callback<ReadingVideoTest> {
//            override fun onResponse(
//                call: Call<ReadingVideoTest>,
//                response: Response<ReadingVideoTest>
//            ) {
//                if (response.code() == 200) {
//                    readingVideoTest = response.body()!!
//                }
//            }
//
//            override fun onFailure(call: Call<ReadingVideoTest>, t: Throwable) {
//                println("-- Network error occured")
//            }
//        })
//        return readingVideoTest
//    }
//
//    private fun getGlossarie(id: Int): Glossarie? {
//        var glossarie: Glossarie? = null
//        val call: Call<Glossarie> = Services.EXERCISE_SERVICE.getGlossarie(id)
//        call.enqueue(object : Callback<Glossarie> {
//            override fun onResponse(call: Call<Glossarie>, response: Response<Glossarie>) {
//                if (response.code() == 200) {
//                    glossarie = response.body()!!
//                }
//            }
//
//            override fun onFailure(call: Call<Glossarie>, t: Throwable) {
//                println("-- Network error occured")
//            }
//        })
//        return glossarie
//    }
//

    private fun removeButtons(layout: LinearLayout, btArr: Array<Button>? = null) {
        for (button in this.buttonsArray) {
            layout.removeView(button)
        }
        buttonDemoExample.visibility = View.GONE
        this.buttonsArray = arrayOf()
        this.buttonComplexArray = arrayOf()
        this.newButtonComplexArray = arrayOf()
    }

    //Creates list of buttons in a given view with given names.
    private fun createButtons(
        buttonNames: Array<String>,
        DestLinearLayout: LinearLayout,
        thisContext: Context,
        id: Array<Int> = emptyArray(),
        type: String = "none"
    ): Array<Button> {

        //array of created buttons. can be used to edit them.
        var buttonsArr: Array<Button>
        buttonsArr = arrayOf()
        //creates button item and adds it to a given view
        for (i in buttonNames.indices) {
            val newButton = Button(thisContext)
            newButton.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            newButton.text = buttonNames[i]
            newButton.setOnClickListener {
                Toast.makeText(thisContext, "Add Reading Test here.", Toast.LENGTH_SHORT).show()
            }
            if (enableRole){
                newButton.visibility = View.GONE
            }
            DestLinearLayout.addView(newButton)
            buttonsArr = buttonsArr.plusElement(newButton)


            if (!id.isNullOrEmpty() && type != "none") {
                var tagsObj: Array<Tags>? = arrayOf()
                var rolesObj: Array<Role>? = arrayOf()

                //temporal value
                var call: Call<TagsResponseEmbedded>? = null
                var call2: Call<RoleResponseEmbedded>? = null

                if (type == "readingVideoTest") {
                    call =
                        Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTestTags(id[i])
                    call2 =
                        Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTestRoles(id[i])
                }
                if (type == "glossarie") {
                    call =
                        Services.EXERCISE_SERVICE.getGlossarieTags(id[i])
                    call2 =
                        Services.EXERCISE_SERVICE.getGlossarieRoles(id[i])
                }
                if (type == "sentences") {
                    call =
                        Services.EXERCISE_SERVICE.getSentenceTags(id[i])
                    call2 =
                        Services.EXERCISE_SERVICE.getSentenceRoles(id[i])
                }
                var thisContext = this

                call!!.enqueue(object : Callback<TagsResponseEmbedded> {
                    override fun onResponse(
                        call: Call<TagsResponseEmbedded>,
                        response: Response<TagsResponseEmbedded>
                    ) {
                        if (response.code() == 200) {
                            println("----- TAGS Response body: " + response.body().toString())
                            tagsObj = response.body()!!.embedded!!.embedded
                            var tags : Array<String> = arrayOf()


                            if (!tagsObj.isNullOrEmpty()) {
                                for (tag in tagsObj!!) {
                                    tags = tags.plusElement(tag.text.toString())
                                }
                            }



                            if (enableRole){

                                call2!!.enqueue(object : Callback<RoleResponseEmbedded> {
                                    override fun onResponse(
                                        call2: Call<RoleResponseEmbedded>,
                                        response2: Response<RoleResponseEmbedded>
                                    ) {
                                        if (response2.code() == 200) {
                                            println("----- ROLES Response body: " + response2.body().toString())
                                            rolesObj = response2.body()!!.embedded!!.embedded
                                            var roles : Array<String> = arrayOf()


                                            if (!rolesObj.isNullOrEmpty()) {
                                                for (r in rolesObj!!) {
                                                    roles = roles.plusElement(r.text.toString())
                                                }
                                            }

                                            thisContext.buttonComplexArray =
                                                thisContext.buttonComplexArray.plusElement(
                                                    ButtonComplex(
                                                        newButton,
                                                        tags,
                                                        id[i],
                                                        type,
                                                        rls = roles
                                                    )
                                                )
                                            for (button in buttonComplexArray) {
                                                if (thisContext.buttonComplexArray.isEmpty()){
                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Something went wrong with roles",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    newButtonComplexArray = arrayOf()
                                                    for (i in thisContext.buttonComplexArray.indices) {
                                                        for (j in thisContext.buttonComplexArray[i].roles.indices) {
                                                            println("-----+++++++ROLES Role found: " + thisContext.buttonComplexArray[i].roles[j])
                                                            var rrrrr = thisContext.buttonComplexArray[i].roles[j]
                                                            if (thisContext.buttonComplexArray[i].roles[j].contains(
                                                                    role.toString().toUpperCase()
                                                                )
                                                            ) {
                                                                buttonComplexArray[i].button.visibility = View.VISIBLE
                                                            } else {

                                                            }
                                                        }
                                                        if (buttonComplexArray[i].button.visibility == View.VISIBLE) {
                                                            newButtonComplexArray =
                                                                newButtonComplexArray.plusElement(buttonComplexArray[i])
                                                        }
                                                    }
                                                    var tmp = newButtonComplexArray
                                                    buttonComplexArray = newButtonComplexArray
                                                }
                                            }
                                        } else {
                                            println("-----ROLES Response body: " + response2.body().toString())
                                        }
                                    }

                                    override fun onFailure(
                                        call2: Call<RoleResponseEmbedded>,
                                        t2: Throwable
                                    ) {
                                        println("------ROLES  Network error occurred" + t2.toString())
                                    }
                                })
                            } else {

                                thisContext.buttonComplexArray =
                                    thisContext.buttonComplexArray.plusElement(
                                        ButtonComplex(
                                            newButton,
                                            tags,
                                            id[i],
                                            type
                                        )
                                    )

                            }

                        } else {
                            println("-----TAGS Response body: " + response.body().toString())
                        }
                    }

                    override fun onFailure(
                        call: Call<TagsResponseEmbedded>,
                        t: Throwable
                    ) {
                        println("------TAGS  Network error occurred" + t.toString())
                    }
                })


            }
        }
        this.buttonsArray = buttonsArr
        if (enableRole){
            for (button in buttonComplexArray) {
                if (this.buttonComplexArray.isEmpty()){
                    Toast.makeText(
                        applicationContext,
                        "Something went wrong with roles",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    for (i in this.buttonComplexArray.indices) {
                        buttonComplexArray[i].button.visibility = View.GONE
                    }
                }
            }

        } else {
            Toast.makeText(
                applicationContext,
                "Showing " + this.buttonsArray.size + " results",
                Toast.LENGTH_SHORT
            ).show()
        }



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

        var glossaries: Array<Glossarie>? = null
        var readingVideoTests: Array<ReadingVideoTest>? = null
        var sentences: Array<Sentence>? = null



        if (intent.hasExtra("Role")) {
            this.role = intent.extras!!.getString("Role")!!
            when (role) {
                "ROLE_LEKTOR" -> textViewRole.text = "Role: Lektor"
                "ROLE_USER" -> textViewRole.text = "Role: User"
                "ROLE_ADMIN" -> textViewRole.text = "Role: Admin"
            }
            textViewRole.visibility = VISIBLE
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
                                Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTest(
                                    demo_readingVideoTest_id
                                )
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
                    var buttonNames: Array<String> = emptyArray()
                    var buttonIds: Array<Int> = emptyArray()

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
                                    buttonIds =
                                        buttonIds.plusElement(readingVideoTests!![i].id!!)
                                }
                                println("created buttonNames array")
                                thisContext.createButtons(
                                    buttonNames,
                                    LinearLayoutTaskSelection,
                                    applicationContext,
                                    buttonIds,
                                    "readingVideoTest"
                                )
                                println("created buttons")


                                for (i in thisContext.buttonsArray.indices) {
                                    thisContext.buttonsArray[i].setOnClickListener() {

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
            } else {
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


                } else {
                    val demo_sentence_id: Int = 255
                    var sentence: Sentence? = null
                    var buttonNames: Array<String> = emptyArray()
                    var buttonIds: Array<Int> = emptyArray()

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
                                    } else {
                                        buttonNames =
                                            buttonNames.plusElement("null")
                                    }
                                }
                                println("created buttonNames array")
                                thisContext.createButtons(
                                    buttonNames,
                                    LinearLayoutTaskSelection,
                                    applicationContext,
                                    buttonIds,
                                    "sentences"
                                )
                                println("created buttons")
                                for (i in thisContext.buttonsArray.indices) {
                                    thisContext.buttonsArray[i].setOnClickListener() {

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
                    var buttonNames: Array<String> = emptyArray()
                    var buttonIds: Array<Int> = emptyArray()

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
                                        buttonIds =
                                            buttonIds.plusElement(glossaries!![i].id!!)
                                    }
                                }
                                println("created buttonNames array")
                                thisContext.createButtons(
                                    buttonNames,
                                    LinearLayoutTaskSelection,
                                    applicationContext,
                                    buttonIds,
                                    "glossarie"
                                )
                                println("created buttons")
                                for (i in thisContext.buttonsArray.indices) {
                                    thisContext.buttonsArray[i].setOnClickListener() {

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


        ExerciseGoToSearchButton.setOnClickListener {
            this.switchVisibility(LinearLayoutExerciseSearch, View.GONE)
        }

        exerciseSearchButton.setOnClickListener {
            if (exerciseSearchText.text.toString() == "Search For Exercises by title..." || exerciseSearchText.text.toString() == this.previousSearch) Toast.makeText(
                applicationContext,
                "Write a search phrase first!",
                Toast.LENGTH_SHORT
            ).show()
            else {
                if (this.buttonsArray.isEmpty()){
                    Toast.makeText(
                        applicationContext,
                        "Select non empty category fisrst!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    for (i in this.buttonsArray.indices){
                        if ( this.buttonsArray[i].text.toString().contains(exerciseSearchText.text.toString())){
                            buttonsArray[i].visibility = View.VISIBLE
                        } else {
                            buttonsArray[i].visibility = View.GONE
                        }
                    }
                    this.previousSearch = exerciseSearchText.text.toString()
                }
            }
        }

        exerciseSearchTagButton.setOnClickListener(){
            if (exerciseSearchTagText.text.toString() == "Search by tag..." || exerciseSearchTagText.text.toString() == this.previousSearch) Toast.makeText(
                applicationContext,
                "Write a search phrase first!",
                Toast.LENGTH_SHORT
            ).show()
            else {
                if (this.buttonComplexArray.isEmpty()){
                    Toast.makeText(
                        applicationContext,
                        "Select non empty category first!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    newButtonComplexArray = arrayOf()
                    for (i in this.buttonComplexArray.indices){
                        buttonComplexArray[i].button.visibility = View.GONE
                        for (j in this.buttonComplexArray[i].tags.indices) {
                            if (this.buttonComplexArray[i].tags[j].contains(
                                    exerciseSearchTagText.text.toString()
                                )
                            ) {
                                buttonComplexArray[i].button.visibility = View.VISIBLE
                            }
                        }
                        if (buttonComplexArray[i].button.visibility == View.VISIBLE){
                            newButtonComplexArray = newButtonComplexArray.plusElement(buttonComplexArray[i])
                        }
                    }


                    var glossarieSetIds : ArrayList<Int> = arrayListOf()

                    for (button in newButtonComplexArray) {
                        if (button.type == "glossarie") {

                            if (!glossaries.isNullOrEmpty()) {
                                for (glossarie in glossaries!!) {
                                    if (glossarie.id == button.id) {
                                        glossarieSetIds.add(button.id)
                                    }
                                }
                            }
                        }
                    }
                    for (button in newButtonComplexArray){
                        if (button.type == "glossarie") {

                            if (!glossaries.isNullOrEmpty()) {
                                for (glossarie in glossaries!!) {
                                    button.button.setOnClickListener() {
                                        Toast.makeText(thisContext, "Launching " + exerciseSearchTagText.text.toString() + " set", Toast.LENGTH_SHORT).show()
                                        println("--------------------Launching " + exerciseSearchTagText.text.toString() + " set")

                                        val intent =
                                            Intent(
                                                thisContext,
                                                FalshcardsActivity::class.java
                                            )
                                        intent.putExtra("glossarieSet", glossarieSetIds)
                                        thisContext.startActivity(intent)

                                    }
                                }
                            }
                        }
                    }
                    this.previousSearch = exerciseSearchTagText.text.toString()
                }
            }
        }

        buttonDemoExample.setOnClickListener {


        }

        RoleSwitch.setOnCheckedChangeListener { _, isChecked ->
            val msg = if (isChecked) role else "All"
            enableRole = isChecked
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            RoleSwitch.text = msg
        }
        //Creating buttons for Task Selection List
//        val buttonAmount = 15
//        var buttonNames = Array(buttonAmount) { i -> "TestButton no. $i" }
//        this.createButtons(buttonNames, LinearLayoutTaskSelection, applicationContext)


    }


}

class ButtonComplex(bt: Button, tgs: Array<String>, tmpId:Int = 0, tmpType: String = "none", rls : Array<String> = emptyArray()){
    var button : Button= bt
    var id: Int = tmpId
    var tags : Array<String> = tgs
    var type : String = tmpType
    var roles : Array<String> = rls

}



