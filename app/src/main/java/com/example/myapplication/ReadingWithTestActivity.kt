package com.example.myapplication

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.apiclient.model.ReadingWithTest
import com.example.myapplication.apiclient.service.ReadingWithTestService
import kotlinx.android.synthetic.main.activity_reading_with_test.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings.PluginState
import android.webkit.WebView
import android.text.method.ScrollingMovementMethod
import android.content.res.Resources.NotFoundException
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.R.attr.bottom
import android.R.attr.right
import android.R.attr.top
import android.R.attr.left
import android.content.Intent
import android.view.Gravity
import android.widget.*


class ReadingWithTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading_with_test)
        val readingWithTest = if (intent.hasExtra("readingWithTest"))
            intent.extras!!.getParcelable("readingWithTest")!!
        else
            ReadingWithTestService.getReadingWithTest()

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
