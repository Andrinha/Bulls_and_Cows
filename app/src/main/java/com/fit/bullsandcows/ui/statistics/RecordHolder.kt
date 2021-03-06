package com.fit.bullsandcows.ui.statistics

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fit.bullsandcows.data.Record
import com.fit.bullsandcows.databinding.RecordItemBinding

class RecordHolder(item: View): RecyclerView.ViewHolder(item) {
    private val binding = RecordItemBinding.bind(item)
    fun bind(record: Record) = with(binding){
        textId.text = record.recordId.toString()
        textName.text = record.name
        textAttempts.text = "Attempts: " + record.attempts
        textTime.text = "Time: " + record.time
        textDate.text = record.date
    }
}
