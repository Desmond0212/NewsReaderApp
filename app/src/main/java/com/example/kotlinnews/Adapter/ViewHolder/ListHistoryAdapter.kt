package com.example.kotlinnews.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinnews.DialogActivity
import com.example.kotlinnews.HistoryActivity
import com.example.kotlinnews.Interface.ItemClickListener
import com.example.kotlinnews.Model.HistoryNews
import com.example.kotlinnews.R
import com.example.kotlinnews.VO.NewsVO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.history_news_layout.view.*

class ListHistoryAdapter(var newsList: MutableList<HistoryNews>, var context: Context): RecyclerView.Adapter<ListHistoryAdapter.ListHistoryViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListHistoryViewHolder
    {
        val inflater = LayoutInflater.from(p0.context)
        val itemView = inflater.inflate(R.layout.history_news_layout, p0, false)

        return ListHistoryViewHolder(itemView)
    }

    override fun getItemCount(): Int
    {
        return newsList.size
    }

    override fun onBindViewHolder(p0: ListHistoryViewHolder, p1: Int)
    {
        Picasso.get()
            .load(newsList[p1].image)
            .into(p0.article_image)

        if (newsList[p1].title.length > 65)
        {
            p0.article_title.text = String.format("%s%s", newsList[p1].title.substring(0,65) , "...")
        }
        else
        {
            p0.article_title.text = newsList[p1].title
        }

        p0.setItemClickListener(object: ItemClickListener
        {
            override fun onClick(view: View, position: Int)
            {
                NewsVO.getInstance().itemId = newsList[position].id
                NewsVO.getInstance().itemUrl = newsList[position].url

                val intent = Intent(context, DialogActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)

                HistoryActivity.fa?.finish()
            }
        })
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder)
    {
        newsList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }

    inner class ListHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        private lateinit var itemClickListener: ItemClickListener
        var article_title = itemView.article_title_history
        var article_image = itemView.article_image_history

        init
        {
            itemView.setOnClickListener(this)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener)
        {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(v: View)
        {
            itemClickListener.onClick(v, adapterPosition)
        }
    }
}