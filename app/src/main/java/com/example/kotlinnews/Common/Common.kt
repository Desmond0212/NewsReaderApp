package com.example.kotlinnews.Common

import com.example.kotlinnews.Interface.NewsService
import com.example.kotlinnews.Remote.RetrofitClient

object Common
{
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "3275bd4ddb994f64abcc9e200dcf4182"//3275bd4ddb994f64abcc9e200dcf4182
    val newsService: NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source: String): String
    {
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=") //https://newsapi.org/v2/top-headlines?sources=
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()

        return apiUrl
    }
}

