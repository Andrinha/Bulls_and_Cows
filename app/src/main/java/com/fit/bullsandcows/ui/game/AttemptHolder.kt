package com.fit.bullsandcows.ui.game

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fit.bullsandcows.data.Attempt
import com.fit.bullsandcows.databinding.AttemptItemBinding
import com.fit.bullsandcows.databinding.RecordItemBinding

class AttemptHolder(item: View): RecyclerView.ViewHolder(item) {
    private val binding = AttemptItemBinding.bind(item)
    fun bind(attempt: Attempt) = with(binding){
        textAttempt.text = attempt.attempt
        textBulls.text = attempt.bulls
        textCows.text = attempt.cows
        textGuess.text = attempt.guess
    }
}
