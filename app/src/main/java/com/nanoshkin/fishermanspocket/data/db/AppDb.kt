package com.nanoshkin.fishermanspocket.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nanoshkin.fishermanspocket.data.entities.LureEntity

@Database(
    entities = [
        LureEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    LureTypeConverter::class
)

abstract class AppDb : RoomDatabase() {
    abstract fun getLureDao(): LureDao
}