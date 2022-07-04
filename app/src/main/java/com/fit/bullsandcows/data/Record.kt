package com.fit.bullsandcows.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_table")
data class Record (
    @PrimaryKey(autoGenerate = true)
    val recordId: Int,
    val name: String,
    val time: String,
    val attempts: String,
    val date: String
)