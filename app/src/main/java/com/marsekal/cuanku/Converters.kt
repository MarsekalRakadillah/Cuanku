package com.marsekal.cuanku

import androidx.room.TypeConverter
import java.text.SimpleDateFormat

class Converters {

    @TypeConverter
    fun fromTimestamp(timeStamp: Long?): String? {
        return timeStamp?.let { FORMATTER.format(timeStamp) }
    }

    @TypeConverter
    fun dateToTimestamp(timeStamp: String?): Long? {
        return timeStamp?.let { FORMATTER.parse(it)?.time }
    }

    companion object{

        val FORMATTER = SimpleDateFormat("yyy-MM-dd")
    }
}