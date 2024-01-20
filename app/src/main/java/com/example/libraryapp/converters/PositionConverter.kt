package com.example.libraryapp.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.libraryapp.data.position.Position

class PositionConverter  {
    private val gson = Gson()

    @TypeConverter
    fun fromPosition(position: Position): String {
        return gson.toJson(position)
    }

    @TypeConverter
    fun toPosition(json: String): Position {
        val type = object : TypeToken<Position>() {}.type
        return gson.fromJson(json, type)
    }
}