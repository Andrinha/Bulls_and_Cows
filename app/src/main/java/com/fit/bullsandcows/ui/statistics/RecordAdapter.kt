package com.fit.bullsandcows.ui.statistics

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.bullsandcows.R
import com.fit.bullsandcows.data.Record

class RecordAdapter: RecyclerView.Adapter<RecordHolder>() {
    private var recordList = emptyList<Record>()

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
    @SuppressLint("NotifyDataSetChanged")
    fun addRecord(record: List<Record>) {
        this.recordList = record
        notifyDataSetChanged()
    }
}