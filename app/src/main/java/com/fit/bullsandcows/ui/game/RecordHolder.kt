package com.fit.bullsandcows.ui.game

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fit.bullsandcows.data.Record
import com.fit.bullsandcows.databinding.RecordItemBinding

class RecordHolder(item: View): RecyclerView.ViewHolder(item) {
    private val binding = RecordItemBinding.bind(item)
    fun bind(record: Record) = with(binding){
        textAttempt.text = record.attempt
        textBulls.text = record.bulls
        textCows.text = record.cows
        textGuess.text = record.guess
    }
}
