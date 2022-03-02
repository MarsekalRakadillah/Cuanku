package com.marsekal.cuanku.database

import androidx.room.*
import com.marsekal.cuanku.database.Transactions

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction (transactions: Transactions)

    @Update
    suspend fun updateTransaction(report: Transactions)

    @Delete
    suspend fun deleteTransaction(report: Transactions)

    @Query("SELECT * FROM Transactions")
    suspend fun getTransaction(): List<Transactions>

    @Query("SELECT * FROM Transactions WHERE  id=:transaction_id")
    suspend fun getAll(transaction_id:Int): List<Transactions>
}