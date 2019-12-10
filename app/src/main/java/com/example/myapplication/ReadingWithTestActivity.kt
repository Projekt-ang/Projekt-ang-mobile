package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.apiclient.service.ReadingWithTestServiceBackup
import kotlinx.android.synthetic.main.activity_reading_with_test.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.text.method.ScrollingMovementMethod
import android.webkit.WebViewClient
import android.content.Intent
import android.widget.*
import com.example.myapplication.apiclient.model.ReadingVideoTest
import com.example.myapplication.apiclient.service.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReadingWithTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        /*val call: Call<List<ReadingVideoTest>> = Services.READING_VIDEO_TEST_SERVICE.getAll()
        call.enqueue(object : Callback<List<ReadingVideoTest>> {
            override fun onResponse(call: Call<List<ReadingVideoTest>>, response: Response<List<ReadingVideoTest>>) {
                if (response.code() == 200) {
                    println(response.body())
                }
            }
            override fun onFailure(call: Call<List<ReadingVideoTest>>, t: Throwable) {
                println("Network error occured")
            }
        })*/
        val call: Call<ReadingVideoTest> = Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTest(10)
        call.enqueue(object : Callback<ReadingVideoTest> {
            override fun onResponse(call: Call<ReadingVideoTest>, response: Response<ReadingVideoTest>) {
                if (response.code() == 200) {
                    println(response.body())
                }
            }
            override fun onFailure(call: Call<ReadingVideoTest>, t: Throwable) {
                println("Network error occured")
            }
        })

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading_with_test)
        val readingWithTest = if (intent.hasExtra("readingWithTest"))
            intent.extras!!.getParcelable("readingWithTest")!!
        else
            ReadingWithTestServiceBackup.getReadingWithTest()

        buttonQuestion.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("readingWithTest",readingWithTest)
            startActivity(intent)
        }

        if (readingWithTest.link == null) {

            val textView = TextView(this)
            textView.movementMethod = ScrollingMovementMethod()
            textView.text = readingWithTest.text
            linearLayoutReadingWithTest.addView(textView)
        } else {
            val webview = WebView(this)
            webview.webViewClient = WebViewClient()
            webview.settings.javaScriptEnabled = true
            webview.setWebChromeClient(WebChromeClient())
            webview.loadUrl(readingWithTest.link)
            linearLayoutReadingWithTest.addView(webview)
        }

        if (intent.hasExtra("lookup")) {
            buttonQuestion.text = getString(R.string.close)
            buttonQuestion.setOnClickListener {
                finish()
            }
        }
    }
}
