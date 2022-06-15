package com.fit.bullsandcows.ui.statistics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.bullsandcows.databinding.ActivityStatisticsBinding

class StatisticsActivity : AppCompatActivity() {

    private var _binding: ActivityStatisticsBinding? = null
    private val binding get() = _binding!!

    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RecordAdapter()
        binding.recyclerRecord.layoutManager = LinearLayoutManager(this)
        binding.recyclerRecord.adapter = adapter

        // RecordViewModel
        statisticsViewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]
        statisticsViewModel.readAllData.observe(this) { record ->
            adapter.addRecord(record)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}