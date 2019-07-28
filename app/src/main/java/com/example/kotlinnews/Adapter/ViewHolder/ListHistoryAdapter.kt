package com.example.kotlinnews.Adapter.ViewHolder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlinnews.Model.HistoryNews
import com.example.kotlinnews.R

class ListHistoryAdapter(var newsList: MutableList<HistoryNews>, var context: Context): RecyclerView.Adapter<ListHistoryAdapter.ListHistoryViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListHistoryViewHolder
    {
        val itemView = LayoutInflater.from(context).inflate(R.layout.history_news_layout, p0, false)
        return ListHistoryViewHolder(itemView)
    }

    override fun getItemCount(): Int
    {
        return newsList.size
    }

    override fun onBindViewHolder(p0: ListHistoryViewHolder, p1: Int)
    {
        p0.txtTitle.text = newsList[p1].title
    }

    inner class ListHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        internal var txtTitle: TextView

        init
        {
            txtTitle = itemView.findViewById(R.id.article_title_history)
        }
    }
}