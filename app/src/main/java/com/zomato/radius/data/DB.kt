package com.zomato.radius.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [FacilityEntity::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class FacilityDB: RoomDatabase() {
    abstract fun facilityDao(): FacilityDao
}

@Database(entities = [ExclusionEntity::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class ExclusionDB: RoomDatabase() {
    abstract fun exclusionDao(): ExclusionDao
}

class ListConverter {
    @TypeConverter
    fun fromExclusionList(list: List<ExclusionData>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toExclusionList(json: String): List<ExclusionData> {
        val type = object : TypeToken<List<ExclusionData>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromFacilityList(list: List<OptionData>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toFacilityList(json: String): List<OptionData> {
        val type = object : TypeToken<List<OptionData>>() {}.type
        return Gson().fromJson(json, type)
    }
}