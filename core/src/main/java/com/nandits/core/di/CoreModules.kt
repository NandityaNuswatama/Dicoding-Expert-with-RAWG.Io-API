package com.nandits.core.di

import androidx.room.Room
import com.nandits.core.data.source.remote.network.ApiService
import com.nandits.core.domain.repository.IGameRepository
import com.nandits.core.ui.GameAdapter
import com.nandits.core.ui.StringAdapter
import com.nandits.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<com.nandits.core.data.source.local.GameDatabase>().gameDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("nandits".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), com.nandits.core.data.source.local.GameDatabase::class.java, "Game.dbx")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.rawg.io"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/UGwY2lttaRoHnGd1gpeydmov1LzioQpzYTywtNSJkAU=")
            .add(hostname, "sha256/hS5jJ4P+iQUErBkvoWBQOd1T7VOAYlOVegvv1iMzpxA=")
            .add(hostname, "sha256/R+V29DqDnO269dFhAAB5jMlZHepWpDGuoejXJjprh7A=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { com.nandits.core.data.source.remote.RemoteDataSource(get()) }
    single { com.nandits.core.data.source.local.LocalDataSource(get()) }
    factory { AppExecutors() }
    single <IGameRepository> { com.nandits.core.data.source.GameRepository(get(), get(), get()) }
}

val adapterModule = module {
    factory { GameAdapter() }
    factory { StringAdapter() }
}