package com.fit.bullsandcows.repository

import com.fit.bullsandcows.data.Record
import com.fit.bullsandcows.data.RecordDao

class RecordRepository(private val recordDao: RecordDao) {

    val readAllData = recordDao.readAllData()

    suspend fun addRecord(record: Record) {
        recordDao.addRecord(record)
    }
}