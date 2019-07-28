package com.example.kotlinnews

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.kotlinnews.Model.HistoryNews
import com.example.kotlinnews.VO.NewsVO
import com.google.firebase.database.FirebaseDatabase
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetail : AppCompatActivity()
{
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        alertDialog = SpotsDialog(this)
        alertDialog.show()

        //Web View
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?)
            {
                alertDialog.dismiss()
            }
        }

        if(intent != null)
        {
            if (!intent.getStringExtra("webURL").isEmpty())
            {
                webView.loadUrl(intent.getStringExtra("webURL"))
                performSaveDataToFirebase()
            }
        }
    }

    private fun performSaveDataToFirebase()
    {
        val title = NewsVO.getInstance().newsTitle.toString()
        val image = NewsVO.getInstance().urlToImage.toString()
        val timestamp = System.currentTimeMillis() / 1000
        val ref = FirebaseDatabase.getInstance().getReference("news")
        val newsId = ref.push().key.toString()
        val historyNews = HistoryNews(newsId, timestamp, title, image)

        ref.child(newsId).setValue(historyNews).addOnSuccessListener {
            Log.d("NewsDetail", "News Details Saved to Firebase!")
        }
    }
}
