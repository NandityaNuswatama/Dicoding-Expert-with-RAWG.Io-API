package com.nandits.core.data.source.remote

import com.nandits.core.BuildConfig
import com.nandits.core.data.source.remote.network.ApiResponse
import com.nandits.core.data.source.remote.network.ApiService
import com.nandits.core.data.source.remote.response.detail.DetailResponse
import com.nandits.core.data.source.remote.response.list.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber


class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getListGame(): Flow<ApiResponse<List<Result>>> {
        return flow{
            try {
                val response = apiService.getListGame(BuildConfig.KEY)
                val data = response.results
                if (data.isNotEmpty()){
                    emit(ApiResponse.Success(data))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.d(e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    
    suspend fun getDetailGame(id: Int): Flow<ApiResponse<DetailResponse>>{
        return flow {
            try {
                val response = apiService.getGameDetail(id, BuildConfig.KEY)
                if (response.description_raw.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                }else{
                        emit(ApiResponse.Empty)
                    }
                }catch (e: Exception){
                    emit(ApiResponse.Error(e.toString()))
                    Timber.d(e.message.toString())
                }
            }.flowOn(Dispatchers.IO)
    }
}