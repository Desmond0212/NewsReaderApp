package com.example.kotlinnews

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.Menu
import android.view.MenuItem
import com.example.kotlinnews.Adapter.ViewHolder.ListHistoryViewHolder
import com.example.kotlinnews.Model.HistoryNews
import com.example.kotlinnews.VO.NewsVO
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity()
{
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.title = "History"

        recyclerViewHistory.adapter = adapter
        recyclerViewHistory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        //Adapter Click Listener
        adapter.setOnItemClickListener { item, view ->
            /*Toast.makeText(baseContext, "Slide Left to Delete the item.", Toast.LENGTH_SHORT).show()*/
            val intent =  Intent(baseContext, HistoryNewsActivity::class.java)

            intent.putExtra("webViewUrl", NewsVO.getInstance().historyNewsUrl)
            startActivity(intent)
        }

        latestNewsListener()
    }

    private fun latestNewsListener()
    {
        val ref = FirebaseDatabase.getInstance().getReference("news")
        ref.addChildEventListener(object: ChildEventListener
        {
            override fun onChildAdded(p0: DataSnapshot, p1: String?)
            {
                val newsData = p0.getValue(HistoryNews::class.java) ?: return
                adapter.add(ListHistoryViewHolder(newsData))
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?)
            {
                val newsData = p0.getValue(HistoryNews::class.java) ?: return
                adapter.add(ListHistoryViewHolder(newsData))
            }

            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        val id = item?.itemId

        if (id == R.id.refresh)
        {
            adapter.notifyDataSetChanged()
        }
        else if (id == R.id.back)
        {
            finish()
        }

        return true
    }
}
