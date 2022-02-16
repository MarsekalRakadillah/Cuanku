package com.marsekal.cuanku

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Transactions(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val label: String,
    val amount: Double,
    val description: String
): Serializable