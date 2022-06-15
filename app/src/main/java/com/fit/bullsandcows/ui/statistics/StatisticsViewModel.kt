package com.fit.bullsandcows.ui.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fit.bullsandcows.data.Record
import com.fit.bullsandcows.data.RecordDatabase
import com.fit.bullsandcows.repository.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticsViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Record>>
    private val repository: RecordRepository

    init {
        val recordDao = RecordDatabase.getDatabase(application).recordDao()
        repository = RecordRepository(recordDao)
        readAllData = repository.readAllData
    }

    fun addRecord(record: Record){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecord(record)
        }
    }
}
