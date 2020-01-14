package com.example.kotlinnews

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.*
import android.widget.Toast
import com.example.kotlinnews.Adapter.ViewHolder.ListHistoryAdapter
import com.example.kotlinnews.Model.HistoryNews
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_history.*
import com.google.firebase.database.DataSnapshot
import android.app.Activity

class HistoryActivity : AppCompatActivity()
{
    internal var items: MutableList<HistoryNews> = ArrayList()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ListHistoryAdapter
    lateinit var alertDialog: AlertDialog


    companion object
    {
        private var instance: HistoryActivity? = null

        fun getInstance(): HistoryActivity
        {
            synchronized(HistoryActivity::class.java)
            {
                if (instance == null)
                {
                    instance = HistoryActivity()
                }

                return instance!!
            }
        }

        var fa: Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.title = "History"

        alertDialog = SpotsDialog(this)
        alertDialog.show()

        recyclerViewHistory.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerViewHistory.layoutManager = layoutManager

        fetchFirebaseData()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
        {
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean
            {
                return false
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int)
            {
                (adapter).removeItem(p0)

                Toast.makeText(baseContext, "One news has been deleted temporary.", Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewHistory)

        fa = this
    }

    private fun fetchFirebaseData()
    {
        val db = FirebaseDatabase.getInstance().getReference("news")

        db.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError)
            {
                Log.d("ERROR", "" + p0.message)
            }

            override fun onDataChange(p0: DataSnapshot)
            {
                for (itemSnapShot in p0.children)
                {
                    val item = itemSnapShot.getValue(HistoryNews::class.java)
                    if (item != null)
                    {
                        items.add(item)

                        adapter = ListHistoryAdapter(items, baseContext)
                        adapter.notifyDataSetChanged()
                        recyclerViewHistory.adapter = adapter

                        alertDialog.dismiss()
                    }
                }
            }

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
