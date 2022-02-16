package com.marsekal.cuanku

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time?.toLong()
    }

//    companion object{
//
//        val FORMATTER = SimpleDateFormat("yyy-MM-dd")
//    }
}