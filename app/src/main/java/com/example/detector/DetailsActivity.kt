package com.example.detector

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bird.motiondetector.plugin.MotionDetector
import com.bird.motiondetector.plugin.utilities.ToUpdate
import kotlinx.android.synthetic.main.show_view.*


class DetailsActivity : Activity(), ToUpdate {

    var mList : ArrayList<String>? = null
    var mAdapter : ContentListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_view)

        val recyclerView = recyclerview
        mAdapter = ContentListAdapter(this)
        recyclerView.setAdapter(mAdapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
    }

    override fun onResume() {
        super.onResume()
        MotionDetector.getInstance().getAllEvents(true, this)
    }

    override fun onUpdate(events: List<String>?) {
        if (events == null) {
            showAlertDialogue()
            return
        }
        if (events?.isNotEmpty()) {
            if (mList == null)
                mList = ArrayList()
            else
                mList?.clear()
            mList?.addAll(events)
            mAdapter?.setEvents(mList!!)
        }
    }

    fun showAlertDialogue() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.oh)
        builder.setMessage(R.string.empty_view_msg)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes"){dialogInterface, which ->
            finish()
        }
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}