package com.zomato.radius.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FacilityDao {
    @Query("SELECT * FROM facility")
    fun getAll(): List<FacilityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(facilityEntity: FacilityEntity)

    @Query("DELETE FROM facility")
    fun deleteAll()
}

@Dao
interface ExclusionDao {
    @Query("SELECT * FROM exclusion")
    fun getAll(): List<ExclusionEntity>

    @Insert
    fun insert(exclusionEntity: ExclusionEntity)

    @Query("DELETE FROM exclusion")
    fun deleteAll()
}