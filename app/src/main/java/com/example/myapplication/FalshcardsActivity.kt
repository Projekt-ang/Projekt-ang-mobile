package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.apiclient.model.Glossarie
import com.example.myapplication.apiclient.service.Services
import kotlinx.android.synthetic.main.activity_exercise_selection.*
import kotlinx.android.synthetic.main.activity_falshcards.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FalshcardsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_falshcards)
        FlashcardsButtonMain.visibility = View.INVISIBLE
        var currentI: Int = 0
        var maxI : Int = 0
        var leftSide = "Rhino"
        var rightSide = "A gray animal, which lives in Africa. It has a horn on its nose."

        var glossarie : Glossarie? = null
        var glossarieSet: ArrayList<Int>? = null

        if (intent.hasExtra("glossarie")) {
            glossarie = intent.extras!!.getParcelable("glossarie")!!
            if (glossarie != null) {
                leftSide = glossarie!!.word.toString()
                rightSide = glossarie!!.definition.toString()
            } else {
                leftSide = "--"
                rightSide = "--"
            }
            FlashcardsButtonMain.text = leftSide
            FlashcardsTitle.text = leftSide
            FlashcardsButtonMain.visibility = View.VISIBLE



        } else if(intent.hasExtra("glossarieSet")) {

            glossarieSet = intent.extras!!.getIntegerArrayList("glossarieSet")!!

            if (glossarieSet != null) {

                maxI = glossarieSet.size - 1

                val call: Call<Glossarie> = Services.EXERCISE_SERVICE.getGlossarie(glossarieSet[currentI])
                call.enqueue(object : Callback<Glossarie> {
                    override fun onResponse(call: Call<Glossarie>, response: Response<Glossarie>) {
                        if (response.code() == 200) {
                            glossarie = response.body()!!
                            if (glossarie != null) {
                                leftSide = glossarie!!.word.toString()
                                rightSide = glossarie!!.definition.toString()
                            } else {
                                leftSide = "--"
                                rightSide = "--"
                            }
                            FlashcardsButtonMain.text = leftSide
                            FlashcardsTitle.text = leftSide
                            FlashcardsButtonMain.visibility = View.VISIBLE
                        }
                    }

                    override fun onFailure(call: Call<Glossarie>, t: Throwable) {
                        println("-- Network error occured, code: " + t.toString())
                        FlashcardsButtonMain.visibility = View.VISIBLE
                    }
                })
            }

        } else {
            val call: Call<Glossarie> = Services.EXERCISE_SERVICE.getGlossarie(1)
            call.enqueue(object : Callback<Glossarie> {
                override fun onResponse(call: Call<Glossarie>, response: Response<Glossarie>) {
                    if (response.code() == 200) {
                        glossarie = response.body()!!
                        if (glossarie != null) {
                            leftSide = glossarie!!.word.toString()
                            rightSide = glossarie!!.definition.toString()
                        }else{
                            leftSide = "--"
                            rightSide = "--"
                        }
                        FlashcardsButtonMain.text = leftSide
                        FlashcardsTitle.text = leftSide
                        FlashcardsButtonMain.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<Glossarie>, t: Throwable) {
                    println("-- Network error occured, code: " + t.toString())
                    FlashcardsButtonMain.visibility = View.VISIBLE
                }
            })
        }
        if(intent.hasExtra("demo")) {
            FlashcardsButtonMain.text = leftSide
            FlashcardsTitle.text = leftSide
            FlashcardsButtonMain.visibility = View.VISIBLE
        }
        FlashcardsButtonMain.visibility = View.VISIBLE


        FlashcardsButtonMain.setOnClickListener(){
            if (FlashcardsButtonMain.text == leftSide) FlashcardsButtonMain.text = rightSide
            else FlashcardsButtonMain.text = leftSide
        }
        FlashcardsButtonNext.setOnClickListener(){
            if(currentI < maxI){
                currentI += 1
                if (!glossarieSet.isNullOrEmpty()) {
                    if (glossarieSet!![currentI] != null) {

                        val call: Call<Glossarie> =
                            Services.EXERCISE_SERVICE.getGlossarie(glossarieSet[currentI])
                        call.enqueue(object : Callback<Glossarie> {
                            override fun onResponse(
                                call: Call<Glossarie>,
                                response: Response<Glossarie>
                            ) {
                                if (response.code() == 200) {
                                    glossarie = response.body()!!
                                    if (glossarie != null) {
                                        leftSide = glossarie!!.word.toString()
                                        rightSide = glossarie!!.definition.toString()
                                    } else {
                                        leftSide = "--"
                                        rightSide = "--"
                                    }
                                    FlashcardsButtonMain.text = leftSide
                                    FlashcardsTitle.text = leftSide
                                    FlashcardsButtonMain.visibility = View.VISIBLE
                                }
                            }

                            override fun onFailure(call: Call<Glossarie>, t: Throwable) {
                                println("-- Network error occured, code: " + t.toString())
                                FlashcardsButtonMain.visibility = View.VISIBLE
                            }
                        })
                    }
                }
            } else {
                Toast.makeText(this, "No more left", Toast.LENGTH_SHORT).show()
            }
        }
        FlashcardsButtonPrev.setOnClickListener(){
            if(currentI > 0){
                currentI -= 1
                if (glossarieSet != null) {
                    if (glossarieSet!![currentI] != null) {

                        val call: Call<Glossarie> =
                            Services.EXERCISE_SERVICE.getGlossarie(glossarieSet[currentI])
                        call.enqueue(object : Callback<Glossarie> {
                            override fun onResponse(
                                call: Call<Glossarie>,
                                response: Response<Glossarie>
                            ) {
                                if (response.code() == 200) {
                                    glossarie = response.body()!!
                                    if (glossarie != null) {
                                        leftSide = glossarie!!.word.toString()
                                        rightSide = glossarie!!.definition.toString()
                                    } else {
                                        leftSide = "--"
                                        rightSide = "--"
                                    }
                                    FlashcardsButtonMain.text = leftSide
                                    FlashcardsTitle.text = leftSide
                                    FlashcardsButtonMain.visibility = View.VISIBLE
                                }
                            }

                            override fun onFailure(call: Call<Glossarie>, t: Throwable) {
                                println("-- Network error occured, code: " + t.toString())
                                FlashcardsButtonMain.visibility = View.VISIBLE
                            }
                        })
                    }
                }
            } else {
                Toast.makeText(this, "No more left", Toast.LENGTH_SHORT).show()
            }
        }
    }
}