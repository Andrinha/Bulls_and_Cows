package com.fit.bullsandcows.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.bullsandcows.R
import com.fit.bullsandcows.data.Attempt

class AttemptAdapter: RecyclerView.Adapter<AttemptHolder>() {
    private val attemptList = ArrayList<Attempt>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttemptHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.attempt_item, parent, false)
        return AttemptHolder(view)
    }

    override fun onBindViewHolder(holder: AttemptHolder, position: Int) {
        holder.bind(attemptList[position])
    }

    override fun getItemCount(): Int {
        return attemptList.size
    }
    fun addRecord(attempt: Attempt) {
        attemptList.add(attempt)
        notifyItemInserted(attemptList.size)
    }

    fun clear() {
        val size: Int = attemptList.size
        attemptList.clear()
        notifyItemRangeRemoved(0, size)
    }
}