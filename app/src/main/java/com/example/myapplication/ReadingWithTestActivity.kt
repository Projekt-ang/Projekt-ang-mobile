package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reading_with_test.*
import android.content.Intent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.text.HtmlCompat
import com.example.myapplication.apiclient.model.ReadingVideoTest
import com.example.myapplication.apiclient.service.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReadingWithTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading_with_test)
        var readingVideoTest : ReadingVideoTest? = null
        if (intent.hasExtra("readingVideoTest")) {
            readingVideoTest = intent.extras!!.getParcelable("readingVideoTest")!!
            textView4.text = HtmlCompat.fromHtml(readingVideoTest.text!!, 0)
            if (!readingVideoTest.link.isNullOrEmpty()) {
                val webview = WebView(this)
                webview.webViewClient = WebViewClient()
                webview.settings.javaScriptEnabled = true
                webview.setWebChromeClient(WebChromeClient())
                webview.loadUrl(readingVideoTest.link)
                linearLayoutReadingWithTest.addView(webview, 500, 400)
            }
            buttonQuestion.isEnabled = true
        } else {
            val call: Call<ReadingVideoTest> = Services.READING_VIDEO_TEST_SERVICE.getReadingVideoTest(53)
            call.enqueue(object : Callback<ReadingVideoTest> {
                override fun onResponse(call: Call<ReadingVideoTest>, response: Response<ReadingVideoTest>) {
                    if (response.code() == 200) {
                        readingVideoTest = response.body()!!
                        textView4.text = HtmlCompat.fromHtml(readingVideoTest!!.text!!, 0)
                        buttonQuestion.isEnabled = true
                    }
                }
                override fun onFailure(call: Call<ReadingVideoTest>, t: Throwable) {
                    println("-- Network error occured" + t.message)
                }
            })
        }

        if (intent.hasExtra("lookup")) {
            buttonQuestion.text = getString(R.string.close)
            buttonQuestion.setOnClickListener {
                finish()
            }
        } else {
            buttonQuestion.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("readingVideoTest",readingVideoTest)
                startActivity(intent)
            }
        }

    }
}
