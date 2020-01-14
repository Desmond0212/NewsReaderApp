package com.example.kotlinnews

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.kotlinnews.VO.NewsVO
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_message.view.*

class DialogActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        showDialogAlert()
    }

    private fun showDialogAlert()
    {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_message, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)//.setTitle("Dialog Message")
        val mAlertDialog = mBuilder.show()

        mDialogView.btn_remove.setOnClickListener {
            mAlertDialog.dismiss()
            FirebaseDatabase.getInstance().getReference("news")
                .child(NewsVO.getInstance().itemId!!/*newsList[position].id*/)
                .removeValue()

            val intent = Intent(baseContext, HistoryActivity::class.java)
            intent.putExtra("openHistory", "fromMain")
            startActivity(intent)

            finish()

            Toast.makeText(this, "One of the Selected News has been Deleted.", Toast.LENGTH_SHORT).show()
        }

        mDialogView.btn_view.setOnClickListener {
            mAlertDialog.dismiss()
            val intent = Intent(this, HistoryNewsActivity::class.java)
            intent.putExtra("webViewUrl", NewsVO.getInstance().itemUrl/*newsList[position].url*/)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)

            finish()
        }

        mDialogView.btn_imgClose.setOnClickListener {
            mAlertDialog.dismiss()

            val intent = Intent(baseContext, HistoryActivity::class.java)
            intent.putExtra("openHistory", "fromMain")
            startActivity(intent)

            finish()
        }
    }

    override fun onBackPressed()
    {
        //don't do anything with back button.
    }
}