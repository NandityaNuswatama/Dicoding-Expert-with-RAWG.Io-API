package com.nandits.core.data.source.remote.network

import com.nandits.core.data.source.remote.response.detail.DetailResponse
import com.nandits.core.data.source.remote.response.list.ListGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getListGame(
        @Query("key") key: String
    ): ListGameResponse
    
    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") key: String
    ): DetailResponse
}