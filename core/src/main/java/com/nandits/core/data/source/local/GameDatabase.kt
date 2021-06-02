package com.nandits.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nandits.core.data.source.local.helper.TypeConverter

@Database(entities = [com.nandits.core.data.source.local.GameEntity::class], version = 1, exportSchema = false)
@TypeConverters(com.nandits.core.data.source.local.helper.TypeConverter::class)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): com.nandits.core.data.source.local.GameDao
}