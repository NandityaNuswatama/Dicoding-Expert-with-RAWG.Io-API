package com.nandits.core.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gameDbx")
data class GameEntity(
    @PrimaryKey
    var gameId: Int,
    var name: String= "",
    var rating: Double= 0.0,
    var description: String= "",
    var image: String= "",
    var metaCritic: Int,
    var isFavorite: Boolean = false,
    var platforms: List<String>,
    var genres: List<String>
)

