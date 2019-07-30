package com.example.kotlinnews

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_history_news.*

class HistoryNewsActivity : AppCompatActivity()
{
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_news)

        alertDialog = SpotsDialog(this)
        alertDialog.show()

        //Web View
        webViewHistory.settings.javaScriptEnabled = true
        webViewHistory.webChromeClient = WebChromeClient()
        webViewHistory.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?)
            {
                alertDialog.dismiss()
            }
        }

        if(intent != null)
        {
            if (!intent.getStringExtra("webViewUrl").isEmpty())
            {
                webViewHistory.loadUrl(intent.getStringExtra("webViewUrl"))
            }
        }
    }
}
