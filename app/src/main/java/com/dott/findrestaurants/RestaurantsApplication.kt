package com.dott.findrestaurants

import android.app.Application
import com.dott.findrestaurants.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RestaurantsApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        startKoin {
            System.out.println("KOIN Starting...")
            androidLogger()
            androidContext(this@RestaurantsApplication)
            modules(
                networkModule,
                apiModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}