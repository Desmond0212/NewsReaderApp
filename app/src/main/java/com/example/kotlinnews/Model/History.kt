package com.example.kotlinnews.Model

class History
{
    var author: String? = null
    var title: String? = null
    var description: String? = null
    var url: String? = null
    var urlToImage: String? = null
    var publishedAt: String? = null

    constructor(author: String?, title: String?, description: String?, url: String?, urlToImage: String?, publishedAt: String?)
    {
        this.author = author
        this.title = title
        this.description = description
        this.url = url
        this.urlToImage = urlToImage
        this.publishedAt = publishedAt
    }
}