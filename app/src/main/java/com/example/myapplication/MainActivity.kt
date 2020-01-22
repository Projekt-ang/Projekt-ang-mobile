package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.apiclient.model.User
import com.example.myapplication.apiclient.model.UserAllResponseEmbedded
import com.example.myapplication.apiclient.service.Services
import kotlinx.android.synthetic.main.activity_exercise_selection.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val ADD_TASK_REQUEST = 1



class MainActivity : AppCompatActivity() {
    var thisContext = this
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        var loginButton : Button = findViewById(R.id.loginButton)
        val demoButton : Button = findViewById(R.id.demoButton)

        loginButton.setOnClickListener {
            var usernm = username.text.toString()
            var passwd = password.text.toString()

            var user: User? = null
            var users: Array<User>? = null

            println("downloading Users pages")
            val call: Call<UserAllResponseEmbedded> =
                Services.EXERCISE_SERVICE.getAllUsers()
            call.enqueue(object : Callback<UserAllResponseEmbedded> {
                override fun onResponse(
                    call: Call<UserAllResponseEmbedded>,
                    response: Response<UserAllResponseEmbedded>
                ) {
                    if (response.code() == 200) {
                        println("Response body: " + response.body().toString())
                        users = response.body()!!.embedded!!.embedded
                        for (i in users!!.indices){
                            //HASHING TO BE ADDED HERE
                            if (users!![i].username.toString() == usernm && users!![i].password.toString() == passwd) {
                                val intent = Intent(thisContext, ExerciseSelectionActivity::class.java)
                                intent.putExtra("Role", usernm)
                                thisContext.startActivity(intent)
                            }
                        }
                    }else{

                        Toast.makeText(thisContext, "Network error - sorry", Toast.LENGTH_SHORT).show()
                        println("Response code: " + response.code().toString())
                    }
                    
                }

                override fun onFailure(
                    call: Call<UserAllResponseEmbedded>,
                    t: Throwable
                ) {
                    println("-- Network error occurred" + t.toString())
                }
            })
        }

        demoButton.setOnClickListener {
            val intent = Intent(this, ExerciseSelectionActivity::class.java)
            intent.putExtra("Role", "demo1")
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }
    }
}
