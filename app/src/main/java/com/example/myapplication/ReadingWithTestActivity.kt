package com.example.myapplication

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
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

class ReadingWithTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading_with_test)

        val readingWithTest = ReadingWithTestService.getReadingWithTest()
        if (readingWithTest.link == null) {

            val textView = TextView(this)
            textView.movementMethod = ScrollingMovementMethod()
            textView.text = readingWithTest.text
            readingWithTestLayout.addView(textView)
        } else {
            val webview = WebView(this)
            webview.webViewClient = WebViewClient()
            webview.settings.javaScriptEnabled = true
            webview.settings.javaScriptCanOpenWindowsAutomatically = true
            webview.settings.pluginState = PluginState.ON
            webview.settings.mediaPlaybackRequiresUserGesture = false
            webview.setWebChromeClient(WebChromeClient())
            webview.loadUrl(readingWithTest.link)
            readingWithTestLayout.addView(webview)
        }
        val button = Button(this)
        button.text = "Quiz"
        readingWithTestLayout.addView(button)
    }
}
