package com.example.riyadal_qulub.db

import androidx.room.TypeConverter
import com.example.riyadal_qulub.entity.Log
import com.google.gson.Gson
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDatesToGson(dates:List<Date>):String{
        return Gson().toJson(dates)
    }
    @TypeConverter

    fun fromGsonToDates(dates:String):List<Date>{
        return Gson().fromJson(dates, Array<Date>::class.java).toList()
    }


    @TypeConverter
    fun fromLogsToGson(logs:List<Log>):String{
        return Gson().toJson(logs)
    }
    @TypeConverter
    fun fromGsonToLogs(logs:String):List<Log>{
        return Gson().fromJson(logs, Array<Log>::class.java).toList()
    }


}