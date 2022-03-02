package com.marsekal.cuanku.database

import android.content.Context
import androidx.room.*
import androidx.room.Database

@Database(
    entities = [Transactions::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class TransactionDB : RoomDatabase(){

    abstract fun transactionDao() : TransactionDao

    companion object {

        @Volatile private var instance : TransactionDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TransactionDB::class.java,
            "transaction12345.db"
        ).build()

    }
}