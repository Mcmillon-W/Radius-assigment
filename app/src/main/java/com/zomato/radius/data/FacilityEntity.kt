package com.zomato.radius.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "facility")
data class FacilityEntity (
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "options") val options: List<OptionData>?
): Serializable

@Entity(tableName = "exclusion")
data class ExclusionEntity (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "pair") var pair: List<ExclusionData>? = null
): Serializable