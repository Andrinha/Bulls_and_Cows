package com.fit.bullsandcows.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fit.bullsandcows.data.Attempt
import com.fit.bullsandcows.data.Record

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRecord(record: Record)

    @Query("SELECT * FROM record_table")
    fun readAllData(): LiveData<List<Record>>
}