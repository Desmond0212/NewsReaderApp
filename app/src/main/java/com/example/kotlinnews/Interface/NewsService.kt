package com.example.kotlinnews.Interface

import com.example.kotlinnews.Model.News
import com.example.kotlinnews.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService
{
    @get:GET("v2/sources?apiKey=3275bd4ddb994f64abcc9e200dcf4182") //v2/sources?apiKey=3275bd4ddb994f64abcc9e200dcf4182
    val source: Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}