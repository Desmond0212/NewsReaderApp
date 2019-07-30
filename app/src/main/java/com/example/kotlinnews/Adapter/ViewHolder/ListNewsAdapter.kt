package com.example.kotlinnews.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinnews.Common.DateFormatParser
import com.example.kotlinnews.Interface.ItemClickListener
import com.example.kotlinnews.Model.Article
import com.example.kotlinnews.NewsDetail
import com.example.kotlinnews.R
import com.example.kotlinnews.VO.NewsVO
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*

class ListNewsAdapter(val articleList: MutableList<Article>, private val context: Context): RecyclerView.Adapter<ListNewsViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListNewsViewHolder
    {
        val inflater = LayoutInflater.from(p0.context)
        val itemView = inflater.inflate(R.layout.news_layout, p0, false)

        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int
    {
        return articleList.size
    }

    override fun onBindViewHolder(p0: ListNewsViewHolder, p1: Int)
    {
        //Load & Fetch Images
        Picasso.get()
            .load(articleList[p1].urlToImage)
            .into(p0.article_image)

        if (articleList[p1].title!!.length > 65)
        {
            p0.article_title.text = String.format("%s%s", articleList[p1].title!!.substring(0,65) , "...")
        }
        else
        {
            p0.article_title.text = articleList[p1].title!!
        }

        if (articleList[p1].publishedAt != null)
        {
            var date: Date? = null

            try
            {
                date = DateFormatParser.parse(articleList[p1].publishedAt!!)
            }
            catch(Ex: ParseException)
            {
                Ex.printStackTrace()
            }

            if (date != null)
            {
                p0.article_time.setReferenceTime(date.time)
            }
            else {}
        }

        p0.setItemClickListener(object: ItemClickListener
        {
            override fun onClick(view: View, position: Int)
            {
                val detail =  Intent(context, NewsDetail::class.java)
                detail.putExtra("webURL", articleList[position].url)
                detail.putExtra("title", articleList[position].title)
                detail.putExtra("image", articleList[position].urlToImage)

                NewsVO.getInstance().newsTitle = articleList[position].title
                NewsVO.getInstance().urlToImage = articleList[position].urlToImage
                NewsVO.getInstance().url = articleList[position].url

                detail.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(detail)
            }
        })
    }
}