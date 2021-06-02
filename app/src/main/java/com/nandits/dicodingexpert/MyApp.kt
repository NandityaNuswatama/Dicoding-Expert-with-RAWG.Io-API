package com.nandits.dicodingexpert

import android.app.Application
import com.nandits.core.di.adapterModule
import com.nandits.core.di.databaseModule
import com.nandits.core.di.networkModule
import com.nandits.core.di.repositoryModule
import com.nandits.dicodingexpert.di.useCaseModule
import com.nandits.dicodingexpert.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin{
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                adapterModule,
                
            )
        }
    }
}