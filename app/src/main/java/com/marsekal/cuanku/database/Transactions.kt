package com.marsekal.cuanku.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.util.*

@Entity
data class Transactions(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val label: String,
    val amount: Double,
    val description: String,
//    @TypeConverters(Converters::class)
//    val date: String
): Serializable