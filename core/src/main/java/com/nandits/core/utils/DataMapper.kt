package com.nandits.core.utils

import com.nandits.core.data.source.local.GameEntity
import com.nandits.core.data.source.remote.response.detail.DetailResponse
import com.nandits.core.data.source.remote.response.list.Result
import com.nandits.core.domain.model.Game

object DataMapper {
    fun mapResponseEntity(input: List<Result>): List<GameEntity>{
        val gameList = ArrayList<GameEntity>()
        input.map {
            val mGame = GameEntity(
                gameId = it.id,
                name = it.name,
                image = it.background_image,
                description = FETCH,
                metaCritic = it.metacritic,
                rating = it.rating,
                platforms = emptyList(),
                genres = emptyList(),
                isFavorite = false
            )
            gameList.add(mGame)
        }
        return gameList
    }
    
    fun mapListEntityDomain(input: List<GameEntity>): List<Game> =
        input.map {
            Game(
                gameId = it.gameId,
                name = it.name,
                rating = it.rating,
                description = it.description,
                image = it.image,
                metaCritic = it.metaCritic,
                isFavorite = it.isFavorite,
                platform = it.platforms,
                genre = it.genres
            )
        }
    
    fun mapEntityDomain(input: GameEntity): Game =
        Game(
            gameId = input.gameId,
            name = input.name,
            rating = input.rating,
            description = input.description,
            image = input.image,
            metaCritic = input.metaCritic,
            isFavorite = input.isFavorite,
            platform = input.platforms,
            genre = input.genres
        )
    
    fun mapDomainEntity(input: Game) =
        GameEntity(
            gameId = input.gameId,
            name = input.name,
            rating = input.rating,
            description = input.description,
            image = input.image,
            metaCritic = input.metaCritic,
            isFavorite = input.isFavorite,
            platforms = input.platform,
            genres = input.genre
        )
    
    fun mapDetailToEntity(input: DetailResponse): GameEntity {
        val listPlatform = mutableListOf<String>()
        input.parent_platforms.map {
            listPlatform.add(it.platform.name)
        }
        val listGenre = mutableListOf<String>()
        input.genres.map {
            listGenre.add(it.name)
        }
        return GameEntity(
            gameId = input.id,
            name = input.name,
            rating = input.rating,
            description = input.description_raw,
            image = input.background_image,
            metaCritic = input.metacritic,
            platforms = listPlatform,
            genres = listGenre
        )
    }
    
    const val FETCH = "fetch"
}