package com.example.kotlinnews

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
/*import android.support.v7.app.AlertDialog*/
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.kotlinnews.Adapter.ViewHolder.ListSourceAdapter
import com.example.kotlinnews.Common.Common
import com.example.kotlinnews.Interface.NewsService
import com.example.kotlinnews.Model.News
import com.example.kotlinnews.Model.WebSite
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity()
{
    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //To Initialize Cache Database
        Paper.init(this)

        //To Initialize Service
        mService = Common.newsService

        //To Initialize View
        swipe_to_refresh.setOnRefreshListener {
            loadWebsiteSource(true)
        }

        recycler_view_source_news.hasFixedSize()
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager
        dialog = SpotsDialog(this)

        loadWebsiteSource(false)

        btnHistory.setOnClickListener {
            val intent = Intent(baseContext, HistoryActivity::class.java)
            intent.putExtra("openHistory", "fromMain")
            startActivity(intent)
        }
    }

    private fun loadWebsiteSource(isRefresh: Boolean)
    {
        if (!isRefresh)
        {
            val cache = Paper.book().read<String>("cache")

            if (cache != null && !cache.isBlank() && cache != "null")
            {
                val webSite = Gson().fromJson<WebSite>(cache, WebSite::class.java)
                adapter = ListSourceAdapter(baseContext, webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            }
            else
            {
                dialog.show()
                mService.source.enqueue(object: retrofit2.Callback<WebSite>{

                    override fun onFailure(call: Call<WebSite>, t: Throwable)
                    {
                        Toast.makeText(baseContext, "Failed to load news website!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>)
                    {
                        adapter = ListSourceAdapter(baseContext, response.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter

                        //Save to Cache
                        Paper.book().write("cache", Gson().toJson(response.body()!!))
                        dialog.dismiss()
                    }
                })
            }
        }
        else
        {
            mService.source.enqueue(object: retrofit2.Callback<WebSite>{

                override fun onFailure(call: Call<WebSite>, t: Throwable)
                {
                    Toast.makeText(baseContext, "Failed to load news website!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>)
                {
                    adapter = ListSourceAdapter(baseContext, response.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter

                    //Save to Cache
                    Paper.book().write("cache", Gson().toJson(response.body()!!))
                    swipe_to_refresh.isRefreshing = false
                }
            })

            swipe_to_refresh.isRefreshing = true
        }
    }
}
