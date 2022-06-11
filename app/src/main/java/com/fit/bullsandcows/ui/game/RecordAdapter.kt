package com.fit.bullsandcows.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.bullsandcows.R
import com.fit.bullsandcows.data.Record

class RecordAdapter: RecyclerView.Adapter<RecordHolder>() {
    private val recordList = ArrayList<Record>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return RecordHolder(view)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        holder.bind(recordList[position])
    }

    override fun getItemCount(): Int {
        return recordList.size
    }
    fun addRecord(record: Record) {
        recordList.add(record)
        notifyItemInserted(recordList.size)
    }

    fun clear() {
        val size: Int = recordList.size
        recordList.clear()
        notifyItemRangeRemoved(0, size)
    }
}