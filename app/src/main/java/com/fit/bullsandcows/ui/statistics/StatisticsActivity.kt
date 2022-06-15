package com.fit.bullsandcows.ui.statistics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.bullsandcows.data.Record
import com.fit.bullsandcows.data.RecordDatabase
import com.fit.bullsandcows.databinding.ActivityStatisticsBinding
import com.fit.bullsandcows.repository.RecordRepository

class StatisticsActivity : AppCompatActivity() {

    var adapter = RecordAdapter()
    private var _binding: ActivityStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerRecord.layoutManager = LinearLayoutManager(this)
        binding.recyclerRecord.adapter = adapter

        val userDao = RecordDatabase.getDatabase(application).recordDao()
        val repository = RecordRepository(userDao)
        val readAllData = repository.readAllData

        readAllData.observe(this) { record ->
            record.forEach {
                adapter.addRecord(Record(
                    it.recordId,
                    it.name,
                    it.time,
                    it.attempts
                ))
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}