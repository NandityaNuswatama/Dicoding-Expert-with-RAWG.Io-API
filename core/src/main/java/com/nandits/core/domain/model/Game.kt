package com.nandits.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    var gameId: Int,
    var name: String= "",
    var rating: Double= 0.0,
    var description: String= "",
    var image: String= "",
    var metaCritic: Int,
    var platform: List<String>,
    var genre: List<String>,
    var isFavorite: Boolean
): Parcelable
