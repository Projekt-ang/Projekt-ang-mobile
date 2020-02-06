package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.apiclient.model.JWTToken
import com.example.myapplication.apiclient.model.JWTUtils
import com.example.myapplication.apiclient.model.User
import com.example.myapplication.apiclient.model.UsernamePasswordParams
import com.example.myapplication.apiclient.service.Services
import com.google.gson.JsonObject
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

            val json = JsonObject()
            json.addProperty("username", usernm)
            json.addProperty("password", passwd)

            val params = UsernamePasswordParams(usernm, passwd)
            val call: Call<JWTToken> = Services.LOGIN_SERVICE.authenticate(json)
            call.enqueue(object : Callback<JWTToken> {
                override fun onResponse(
                    call: Call<JWTToken>,
                    response: Response<JWTToken>
                ) {
                    if (response.code() == 200) {
                        var jwt = response.body()!!.text!!
                        val lastIndex = jwt.lastIndexOf('.')
                        jwt = jwt.substring(0, lastIndex)

                        val text = JWTUtils.decoded(jwt)
                        val regex = Regex("(,\"roles\":\")([A-Za-z]+[A-Za-z_]*)(\")")
                        val matchResults = regex.find(text)

                        val role = matchResults!!.groups[2]!!.value


                        val intent = Intent(thisContext, ExerciseSelectionActivity::class.java)
                        intent.putExtra("Role", role)
                        thisContext.startActivity(intent)
                    }else{
                        Toast.makeText(thisContext, "Network error - sorry", Toast.LENGTH_SHORT).show()
                        println("Response code: " + response.code().toString())
                    }
                    
                }

                override fun onFailure(
                    call: Call<JWTToken>,
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
