package com.nandits.core.data.source

import com.nandits.core.data.NetworkBoundResource
import com.nandits.core.data.Resource
import com.nandits.core.data.source.local.LocalDataSource
import com.nandits.core.data.source.remote.RemoteDataSource
import com.nandits.core.data.source.remote.network.ApiResponse
import com.nandits.core.data.source.remote.response.detail.DetailResponse
import com.nandits.core.data.source.remote.response.list.Result
import com.nandits.core.domain.model.Game
import com.nandits.core.domain.repository.IGameRepository
import com.nandits.core.utils.AppExecutors
import com.nandits.core.utils.DataMapper
import com.nandits.core.utils.DataMapper.FETCH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IGameRepository {
    override fun getGameList(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<Result>>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getGameList().map {
                    DataMapper.mapListEntityDomain(it)
                }
            }
    
            override fun shouldFetch(data: List<Game>?): Boolean {
                return data == null || data.isEmpty()
            }
    
            override suspend fun createCall(): Flow<ApiResponse<List<Result>>> {
                return remoteDataSource.getListGame()
            }
    
            override suspend fun saveCallResult(data: List<Result>) {
                localDataSource.insertGame(DataMapper.mapResponseEntity(data))
            }
        }.asFlow()
    
    override fun getDetailGame(id: Int): Flow<Game> {
        return localDataSource.getGame(id).map { DataMapper.mapEntityDomain(it) }
    }
    
    override fun getGameDetail(id: Int): Flow<Resource<Game>> =
        object : com.nandits.core.data.NetworkBoundResource<Game, DetailResponse>() {
            override fun loadFromDB(): Flow<Game> {
                return localDataSource.getGame(id).map {
                    DataMapper.mapEntityDomain(it)
                }
            }
    
            override fun shouldFetch(data: Game?): Boolean {
                return data?.description == FETCH
            }
    
            override suspend fun createCall(): Flow<ApiResponse<DetailResponse>> {
                return remoteDataSource.getDetailGame(id)
            }
    
            override suspend fun saveCallResult(data: DetailResponse) {
                localDataSource.updateDataGame(DataMapper.mapDetailToEntity(data))
            }
        }.asFlow()
    
    override fun getFavoriteGame(): Flow<List<Game>> {
        return localDataSource.getFavoriteGame().map {
            DataMapper.mapListEntityDomain(it)
        }
    }
    override fun setFavoriteGame(game: Game, state: Boolean) {
        val entity = DataMapper.mapDomainEntity(game)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGame(entity, state) }
    }
}