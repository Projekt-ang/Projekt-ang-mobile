package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.myapplication.apiclient.model.ReadingVideoTest
import com.example.myapplication.apiclient.service.Services
import kotlinx.android.synthetic.main.activity_reading_with_test.*
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
                webView.webViewClient = WebViewClient()
                webView.settings.javaScriptEnabled = true
                webView.setWebChromeClient(WebChromeClient())
                webView.loadUrl(readingVideoTest.link)
                webView.visibility= View.VISIBLE
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
                startActivityForResult(intent, 2)
            }
        }

    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 2) {
            finish()
        }
    }
}
