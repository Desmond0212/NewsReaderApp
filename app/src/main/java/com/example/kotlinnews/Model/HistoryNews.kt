package com.example.kotlinnews.Model

class HistoryNews(val id: String, val timestamp: Long, val title: String, val image: String)
{
    constructor(): this("",-1, "", "")
}