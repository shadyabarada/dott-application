package com.dott.findrestaurants

import android.app.Application
import com.dott.findrestaurants.koin.apiModule
import com.dott.findrestaurants.koin.networkModule
import com.dott.findrestaurants.koin.repositoryModule
import com.dott.findrestaurants.koin.viewModelModule
import org.koin.core.context.startKoin

class RestaurantsApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                apiModule,
                viewModelModule,
                repositoryModule,
                networkModule,
            )
        }
    }
}