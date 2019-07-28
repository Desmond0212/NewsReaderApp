package com.example.kotlinnews.Adapter.ViewHolder

import com.example.kotlinnews.Model.HistoryNews
import com.example.kotlinnews.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.history_news_layout.view.*

class ListHistoryViewHolder(val newsData: HistoryNews): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int)
    {
        viewHolder.itemView.article_title_history.text = newsData.title
        val targetImageView = viewHolder.itemView.article_image_history
        Picasso.get().load(newsData.image).into(targetImageView)

        val ref = FirebaseDatabase.getInstance().getReference("/news")

        ref.addListenerForSingleValueEvent(object: ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot) {}

            override fun onCancelled(p0: DatabaseError) {}
        })
    }

    override fun getLayout(): Int
    {
        return R.layout.history_news_layout
    }
}