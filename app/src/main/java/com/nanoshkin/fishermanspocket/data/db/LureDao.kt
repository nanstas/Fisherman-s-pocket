package com.nanoshkin.fishermanspocket.data.db

import androidx.room.*
import com.nanoshkin.fishermanspocket.data.entities.LureEntity
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.models.LureType
import kotlinx.coroutines.flow.Flow

@Dao
interface LureDao {
    @Transaction
    @Query("SELECT * FROM LureEntity")
    fun getAllLures(): Flow<List<Lure>>

    @Transaction
    @Query("SELECT * FROM LureEntity WHERE id = :id")
    fun getLureById(id: Int): Flow<Lure>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLure(lure: LureEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLures(lures: List<LureEntity>)
}

class LureTypeConverter {
    @TypeConverter
    fun toLureType(type: String): LureType = LureType.valueOf(type)

    @TypeConverter
    fun fromLureType(type: LureType): String = type.name
}
