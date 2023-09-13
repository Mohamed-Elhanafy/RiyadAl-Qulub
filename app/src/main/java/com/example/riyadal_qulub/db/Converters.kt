package com.example.riyadal_qulub.db

import androidx.room.TypeConverter
import com.example.riyadal_qulub.entity.Log
import com.google.gson.Gson
import java.util.Date

class Converters {

    @TypeConverter
    fun fromStringListToGson(stringList:List<String>):String{
        return Gson().toJson(stringList)
    }

    @TypeConverter
    fun fromGsonToStringList(stringList:String):List<String>{
        return Gson().fromJson(stringList, Array<String>::class.java).toList()
    }


}