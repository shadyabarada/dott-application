package com.dott.findrestaurants.koin

import android.app.Application
import androidx.room.Room
import com.dott.findrestaurants.database.RestaurantsDatabase
import com.dott.findrestaurants.map.dao.RestaurantsDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): RestaurantsDatabase {
        return Room.databaseBuilder(application, RestaurantsDatabase::class.java, "Restaurants")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: RestaurantsDatabase): RestaurantsDao {
        return database.restaurantsDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }


}