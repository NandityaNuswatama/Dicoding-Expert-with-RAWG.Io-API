package com.nandits.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nandits.core.data.source.local.helper.TypeConverter

@Database(entities = [GameEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}