package com.example.kotlinnews.VO

class NewsVO private constructor()
{
    companion object
    {
        private var instance: NewsVO? = null

        fun getInstance(): NewsVO
        {
            synchronized(NewsVO::class.java)
            {
                if (instance == null)
                {
                    instance = NewsVO()
                }

                return instance!!
            }
        }
    }

    var newsTitle: String? = null
    var urlToImage: String? = null
    var url: String? = null
    var historyNewsUrl: String? = null
}